package com.mry5l.apiclientsdk.strategy.impl;

import com.google.gson.Gson;
import com.mry5l.apiclientsdk.apiservice.ApiService;
import com.mry5l.apiclientsdk.common.ErrorCode;
import com.mry5l.apiclientsdk.constant.ResponseResult;
import com.mry5l.apiclientsdk.exception.BusinessException;
import com.mry5l.apiclientsdk.model.params.AiMessageParams;
import com.mry5l.apiclientsdk.strategy.BaseStrategy;
import lombok.extern.slf4j.Slf4j;


/**
 * Post请求ChatGPT聊天接口
 * @author YJL
 * @version 1.0
 */
@Slf4j
public class AiMessageStrategy implements BaseStrategy {

    @Override
    public String handlerRequest(String restfulUrl, String params, ApiService apiService) {
        log.info("url = {} , params = {}", restfulUrl, params);
        Gson gson = new Gson();
        AiMessageParams aiMessageParams = null;
        try {
            aiMessageParams = gson.fromJson(params, AiMessageParams.class);
        } catch (Exception e) {
            log.error("转换json出现异常,错误为: {},输入参数为: {}", e.getMessage(), params);
            return ResponseResult.BASE_RESULT;
        }
        if (aiMessageParams == null) {
            aiMessageParams = new AiMessageParams();
        }
        return apiService.definitionRequest(restfulUrl, aiMessageParams);
    }
}
