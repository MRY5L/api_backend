package com.mry5l.apigateway.config;

import com.mry5l.apiclientsdk.common.ResultUtils;
import com.mry5l.apiclientsdk.model.entity.InterfaceInfo;
import com.mry5l.apiclientsdk.model.entity.User;
import com.mry5l.apiclientsdk.service.InnerInterfaceInfoService;
import com.mry5l.apiclientsdk.service.InnerUserInterInfoService;
import com.mry5l.apiclientsdk.service.InnerUserService;
import com.mry5l.apiclientsdk.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 全局过滤
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService interfaceInfoService;

    @DubboReference
    private InnerUserInterInfoService userInterInfoService;

    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");
    private static final String INTERFACE_HOST = "http://localhost:8701";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1. 用户发送请求到API网关 -> 已在配置文件中完成
        //2. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String path = INTERFACE_HOST + request.getPath().value();
        String method = request.getMethod().toString();
        log.info("请求唯一标识: {}", request.getId());
        log.info("请求路径:{}", path);
        log.info("请求方法: {}", method);
        log.info("请求参数: {}", request.getQueryParams());
        log.info("请求来源地址: {}", request.getRemoteAddress());
        String hostString = request.getLocalAddress().getHostString();
        log.info("请求来源地址: {}", hostString);
        //3.访问控制 -> 黑白名单
        ServerHttpResponse response = exchange.getResponse();
        if (!IP_WHITE_LIST.contains(hostString)) {
            return HandelNoAuth(response, "IP不正确");
        }
        //4. 用户鉴权(判断 ak,sk是否合法)
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = null;
        try {
            body = URLDecoder.decode(headers.getFirst("body"), "utf8");
        } catch (UnsupportedEncodingException e) {
            log.error("解密body出现错误,异常信息为: " + e);
            return HandelInvokeError(response, "系统异常");
        }

        //在数据库中查询是否已分配给用户
        User invokeUser = null;
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);
        } catch (Exception e) {
            log.error("远程调用出错 innerUserService.getInvokeUser(accessKey),异常信息: " + e);
        }
        if (invokeUser == null) {
            return HandelNoAuth(response, "公钥未分配");
        }

        if (Long.parseLong(nonce) > 10000) {
            return HandelNoAuth(response, "随机数过大");
        }
        //时间戳和当前时间不能超过五分钟
        Long currentTime = System.currentTimeMillis() / 1000;
        final Long FIVE_MINUTES = 60 * 5L;
        if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES) {
            return HandelNoAuth(response, "请求有效时间已过");
        }
        //从数据库中查到 secretKey
        String secretKey = invokeUser.getSecretKey();
        String serverSign = SignUtil.genSign(body, secretKey);
        if (sign == null || !sign.equals(serverSign)) {
            return HandelNoAuth(response, "密钥异常");
        }
        //5. 请求的模拟接口是否存在
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = interfaceInfoService.getInterfaceInfo(path, method);
        } catch (Exception e) {
            log.error("远程调用出错 interfaceInfoService.getInterfaceInfo(path, method),异常信息: " + e);
        }

        if (interfaceInfo == null) {
            return HandelNoAuth(response, "接口不存在");
        }
        //是否还有调用次数
        boolean residueCount = userInterInfoService.invokeResidueCount(invokeUser.getId());
        if (!residueCount) {
            return HandelNoAuth(response, "没有调用次数");
        }
        //6.请求转发,调用模拟接口 + 响应日志
        return responseLog(exchange, chain, interfaceInfo.getId(), invokeUser.getId());
    }

    @Override
    public int getOrder() {
        return -1;
    }

    /**
     * 响应日志和处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    private Mono<Void> responseLog(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceId, long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            //缓存数据工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            HttpStatus statusCode = originalResponse.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                ServerHttpResponseDecorator decoratorResponse = new ServerHttpResponseDecorator(originalResponse) {
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        //7.调用成功,接口调用次数 + 1
                                        try {
                                            userInterInfoService.invokeCount(interfaceId, userId);
                                        } catch (Exception e) {
                                            log.error("远程调用出错 userInterInfoService.invokeCount(interfaceId, userId);,异常信息: " + e);
                                        }
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放内存
                                        //构建日志
                                        StringBuilder builder = new StringBuilder(200);
                                        ArrayList<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8);
                                        builder.append(data);
                                        //打印日志
                                        log.info("响应结果builder: {} ", builder);
                                        log.info("响应结果rspArgs: {}", rspArgs);
                                        return bufferFactory.wrap(content);
                                    }));
                        } else {
                            //9. 调用失败,返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                //设置response对象为装饰过的
                return chain.filter(exchange.mutate().

                        response(decoratorResponse).

                        build());
            }
            return chain.filter(exchange);//降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }


    /**
     * 响应403
     *
     * @param response
     * @return
     */
    private Mono<Void> HandelNoAuth(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        // return response.setComplete();
        return response.writeWith(Mono.just(response.bufferFactory().wrap(message.getBytes())));
    }


    /**
     * 响应500
     *
     * @param response
     * @return
     */
    private Mono<Void> HandelInvokeError(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(message.getBytes())));
    }
}