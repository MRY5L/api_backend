package com.mry5l.apiclientsdk.strategy.impl;

import com.mry5l.apiclientsdk.apiservice.ApiService;
import com.mry5l.apiclientsdk.strategy.BaseStrategy;

/**
 * 占位类
 *
 * @author YJL
 * @version 1.0
 */
public class Present implements BaseStrategy {
    @Override
    public String handlerRequest(String restfulUrl, String params, ApiService apiService) {
        return apiService.definitionRequest(restfulUrl, " ");
    }
}
