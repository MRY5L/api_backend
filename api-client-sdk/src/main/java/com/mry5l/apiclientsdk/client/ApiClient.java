package com.mry5l.apiclientsdk.client;

import lombok.Data;


/**
 * 客户端类
 *
 * @author YJL
 * @version 1.0
 */
@Data
public class ApiClient {
    private String accessKey;
    private String secretKey;

    public ApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public ApiClient() {
    }
}
