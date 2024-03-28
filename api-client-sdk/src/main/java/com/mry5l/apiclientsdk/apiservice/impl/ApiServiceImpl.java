package com.mry5l.apiclientsdk.apiservice.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.mry5l.apiclientsdk.apiservice.ApiService;
import com.mry5l.apiclientsdk.client.ApiClient;
import com.mry5l.apiclientsdk.common.ErrorCode;
import com.mry5l.apiclientsdk.constant.MyUrl;
import com.mry5l.apiclientsdk.exception.BusinessException;
import com.mry5l.apiclientsdk.utils.SignUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YJL
 * @version 1.0
 */
@Data
@Slf4j
public class ApiServiceImpl implements ApiService {
    private static final String GATEWAY_HOST = MyUrl.GATEWAY_HOST;


    private ApiClient apiClient;

    /**
     * 封装请求头Map
     *
     * @param body 请求头参数
     * @return 请求头Map
     */
    private Map<String, String> getHeaderMap(String body) {
        HashMap<String, String> keyMap = new HashMap<>();
        keyMap.put("accessKey", apiClient.getAccessKey());
        keyMap.put("nonce", RandomUtil.randomNumbers(4));
        try {
            keyMap.put("body", URLEncoder.encode(body, "utf8"));
        } catch (Exception e) {
            log.error("加密传递参数出错,异常信息为: " + e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "加密传递参数出错");
        }
        keyMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        keyMap.put("sign", SignUtil.genSign(body, apiClient.getSecretKey()));
        return keyMap;
    }

    private <T> String getJson(T arg) {
        return JSONUtil.toJsonStr(arg);
    }

    /**
     * 封装发起请求
     *
     * @param restfulUrl restfulUrl风格请求路径
     * @param arg        参数
     * @param <T>        参数
     * @return 响应结果
     */
    public <T> String definitionRequest(String restfulUrl, T arg) {
        String json = getJson(arg);
        HttpResponse response = HttpRequest.post(GATEWAY_HOST + restfulUrl)
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute()
                .charset(StandardCharsets.UTF_8);
        String result = response.body();
        log.info("SDK 返回状态为: {}", response.getStatus());
        log.info("SDK 返回结果为: {}", result);
        return result;
    }
}
