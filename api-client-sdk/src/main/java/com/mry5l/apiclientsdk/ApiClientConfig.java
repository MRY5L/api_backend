package com.mry5l.apiclientsdk;

import com.mry5l.apiclientsdk.apiservice.ApiService;
import com.mry5l.apiclientsdk.apiservice.impl.ApiServiceImpl;
import com.mry5l.apiclientsdk.client.ApiClient;
import com.mry5l.apiclientsdk.strategy.BaseContext;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * 配置
 * @author YJL
 * @version 1.0
 */
@Configuration
@ConfigurationProperties("api-client")
@Data
@ComponentScan
public class ApiClientConfig {

    /**
     * 公钥
     */
    private String accessKey;

    /**
     * 私钥
     */
    private String secretKey;

    @Bean
    public BaseContext baseContext() {
        ApiServiceImpl apiService = new ApiServiceImpl();
        apiService.setApiClient(new ApiClient(accessKey, secretKey));
        BaseContext baseContext = new BaseContext();
        baseContext.setApiClient(apiService);
        return baseContext;
    }
}
