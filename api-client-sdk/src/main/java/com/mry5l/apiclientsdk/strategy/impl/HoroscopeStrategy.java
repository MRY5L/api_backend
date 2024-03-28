package com.mry5l.apiclientsdk.strategy.impl;

import com.google.gson.Gson;
import com.mry5l.apiclientsdk.apiservice.ApiService;
import com.mry5l.apiclientsdk.constant.ResponseResult;
import com.mry5l.apiclientsdk.model.params.HoroscopeParams;
import com.mry5l.apiclientsdk.strategy.BaseStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * Post请求星座接口
 * @author YJL
 * @version 1.0
 */
@Slf4j
public class HoroscopeStrategy implements BaseStrategy {
    @Override
    public String handlerRequest(String restfulUrl, String params, ApiService apiService) {
        log.info("url = {} , params = {}", restfulUrl, params);
        Gson gson = new Gson();
        HoroscopeParams horoscopeParams = null;
        if (StringUtils.isBlank(params)){
            return ResponseResult.BASE_RESULT;
        }
        try {
            horoscopeParams = gson.fromJson(params, HoroscopeParams.class);
        } catch (Exception e) {
            log.error("转换json出现异常,错误为: {},输入参数为: {}", e.getMessage(),params);
            return ResponseResult.BASE_RESULT;
        }
        return apiService.definitionRequest(restfulUrl, horoscopeParams);
    }
}
