package com.mry5l.apiclientsdk.strategy.impl;

import com.google.gson.Gson;
import com.mry5l.apiclientsdk.apiservice.ApiService;
import com.mry5l.apiclientsdk.constant.ResponseResult;
import com.mry5l.apiclientsdk.model.params.IpInfoParams;
import com.mry5l.apiclientsdk.strategy.BaseStrategy;
import lombok.extern.slf4j.Slf4j;

/**
 * Post请求获取IP信息接口
 * @author YJL
 * @version 1.0
 */
@Slf4j
public class IpInfoStrategy implements BaseStrategy {

    @Override
    public String handlerRequest(String restfulUrl, String params, ApiService apiService) {
        log.info("url = {} , params = {}", restfulUrl, params);
        Gson gson = new Gson();
        IpInfoParams ipInfoParams = null;
        try {
            ipInfoParams = gson.fromJson(params, IpInfoParams.class);
        } catch (Exception e) {
            log.error("转换json出现异常,错误为: {},输入参数为: {}", e.getMessage(), params);
            return ResponseResult.BASE_RESULT;
        }
        if (ipInfoParams == null) {
            ipInfoParams = new IpInfoParams();
        }
        return apiService.definitionRequest(restfulUrl, ipInfoParams);
    }
}
