package com.mry5l.apiclientsdk.strategy;

import com.mry5l.apiclientsdk.apiservice.ApiService;
import com.mry5l.apiclientsdk.constant.MyUrl;
import com.mry5l.apiclientsdk.strategy.impl.AiMessageStrategy;
import com.mry5l.apiclientsdk.strategy.impl.HoroscopeStrategy;
import com.mry5l.apiclientsdk.strategy.impl.IpInfoStrategy;
import com.mry5l.apiclientsdk.strategy.impl.Present;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 策略核心上下文类
 * @author YJL
 * @version 1.0
 */
public class BaseContext {
    private static final Map<String, BaseStrategy> strategyMap = new ConcurrentHashMap<>();
    private ApiService apiService;
    private static final Present PRESENT = new Present();

    static {
        strategyMap.put(MyUrl.IP_URL, new IpInfoStrategy());
        strategyMap.put(MyUrl.DO_CHAT, new AiMessageStrategy());
        strategyMap.put(MyUrl.HOROSCOPE, new HoroscopeStrategy());
        strategyMap.put(MyUrl.RANDOM_SCENERY, PRESENT);
        strategyMap.put(MyUrl.MO_YU, PRESENT);
        strategyMap.put(MyUrl.TALK_LOVE, PRESENT);
        strategyMap.put(MyUrl.RANDOM_WALLPAPER, PRESENT);
    }

    public String handler(String restfulUrl, String params) {
        BaseStrategy baseStrategy = strategyMap.get(restfulUrl);
        return baseStrategy.handlerRequest(restfulUrl, params, apiService);
    }

    public void setApiClient(ApiService apiService) {
        this.apiService = apiService;
    }
}
