package com.mry5l.apiclientsdk.service;


import com.mry5l.apiclientsdk.model.entity.User;

/**
 * 用户服务
 * @author YJL
 * @version 1.0
 */
public interface InnerUserService{

    /**
     * 从数据库中查询是否已分配给用户密钥
     *
     * @param accessKey ...
     * @return ...
     */
    User getInvokeUser(String accessKey);
}
