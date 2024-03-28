package com.mry5l.apiclientsdk.apiservice;



/**
 * @author YJL
 * @version 1.0
 */
public interface ApiService {

    /**
     * 封装发起请求
     * @param restfulUrl 地址
     * @param arg 参数
     * @param <T> ...
     * @return 请求结果
     */
    <T> String definitionRequest(String restfulUrl, T arg);
}
