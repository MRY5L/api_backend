package com.mry5l.apiclientsdk.strategy;

import com.mry5l.apiclientsdk.apiservice.ApiService;

/**
 * 抽出公有方法
 * @author YJL
 * @version 1.0
 */
public interface BaseStrategy {

    String handlerRequest(String restfulUrl, String params, ApiService apiService);
}
