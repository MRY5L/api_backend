package com.mry5l.apiinterface.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.mry5l.apiclientsdk.common.ErrorCode;
import com.mry5l.apiclientsdk.exception.BusinessException;
import com.mry5l.apiclientsdk.model.params.AiMessageParams;
import com.mry5l.apiclientsdk.model.params.HoroscopeParams;
import com.mry5l.apiinterface.constant.TalkLoveConstant;
import com.mry5l.apiinterface.openai.ChatGptService;
import com.mry5l.apiinterface.service.MyInterfaceApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author YJL
 * @version 1.0
 */
@Slf4j
@Service
public class MyInterfaceApiServiceImpl implements MyInterfaceApiService {
    @Resource
    ChatGptService chatGptService;

    @Override
    public String getIpDetailsInfo(String ip) {
        //校验
        if ("127.0.0.1".equals(ip) || ip.startsWith("192.168") || ip.startsWith("0:0:0") || ip.startsWith("0.0.0")) {
            String str = "局域网 IP";
            log.info("当前IP为: {}是局域网IP", ip);
            return str;
        }
        // 1、创建 searcher 对象
        String dbPath = "/www/wwwroot/ipInfolocal/ip2region.xdb";
        // 1、从 dbPath 加载整个 xdb 到内存。
        byte[] cBuff = new byte[0];
        try {
            cBuff = Searcher.loadContentFromFile(dbPath);
        } catch (Exception e) {
            log.error("读取本地ip信息失败,读取路径为: {},异常信息为: {}", dbPath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取本地IP信息失败");
        }

        // 2、使用上述的 cBuff 创建一个完全基于内存的查询对象。
        Searcher searcher = null;
        try {
            searcher = Searcher.newWithBuffer(cBuff);
        } catch (Exception e) {
            log.error("读取本地ip信息加入内存失败读取路径为: {},异常信息为: {}", dbPath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取本地IP信息失败");
        }

        // 3、查询
        String region = null;
        try {
            long sTime = System.nanoTime();
            region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros((long) (System.nanoTime() - sTime));
            System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n", region, searcher.getIOCount(), cost);
        } catch (Exception e) {
            log.error("读取本地ip信息加入内存失败读取路径为: {},异常信息为: {}", dbPath, e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "获取本地IP信息失败");
        } finally {
            try {
                searcher.close();
            } catch (IOException e) {
                e.printStackTrace();
                log.error("searcher.close(); 关闭流异常,异常信息为: " + e);
            }
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{\"youIpInfo\":\"");
        builder.append(ip);
        builder.append("\"},");
        builder.append("\n");
        builder.append("{\"result\":\"");
        builder.append(region);
        builder.append("\"}");
        return builder.toString();
    }

    @Override
    public String doChat(AiMessageParams aiMessage) {
        if (aiMessage == null || aiMessage.getAiMessage() == null) {
            aiMessage = new AiMessageParams();
            aiMessage.setAiMessage("你是作诗大师,来一首关于蔡徐坤的七言古诗绝句");
        }
        String message = aiMessage.getAiMessage();
        return chatGptService.doChat(message);
    }

    @Override
    public String getTalkLove() {
        String message = "";
        Map<Integer, String> TALK_MAP = TalkLoveConstant.getMAP();
        message = TALK_MAP.get(RandomUtil.randomInt(TALK_MAP.size()));
        log.info("随机情话接口,当前情话prompt模版为: {}", message);
        return chatGptService.doChat(message);
    }

    @Override
    public String getRandomScenery() {
        HttpResponse response = HttpRequest
                .get("https://api.vvhan.com/api/wallpaper/views?type=json")
                .execute();
        return response.body();
    }

    @Override
    public String getMoYu() {
        HttpResponse response = HttpRequest
                .get("https://api.vvhan.com/api/moyu?type=json")
                .execute();
        return response.body();
    }

    @Override
    public String getRandomWallpaper() {
        HttpResponse response = HttpRequest
                .get("https://api.vvhan.com/api/bing?rand=sj")
                .execute();
        String html = response.body();
        log.info("获取随机壁纸,原始返回结果为: {}", html);
        // 使用正则表达式匹配href属性的值
        Pattern pattern = Pattern.compile("<a\\s+href\\s*=\\s*\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(html);

        // 如果未找到匹配的内容，直接返回原始结果,无法预览
        if (!matcher.find()) {
            return html;
        }
        String href = matcher.group(1);
        return "{\"url\":\"" + href + "\"}";
    }

    @Override
    public String getHoroscope(HoroscopeParams horoscopeParams) {
        if (horoscopeParams == null || StringUtils.isBlank(horoscopeParams.getHoroscope())) {
            horoscopeParams = new HoroscopeParams();
        }
        String horoscope = horoscopeParams.getHoroscope();
        HttpResponse response = HttpRequest
                .get("https://api.vvhan.com/api/horoscope?type=" + horoscope + "&time=today")
                .execute();
        return response.body();
    }
}
