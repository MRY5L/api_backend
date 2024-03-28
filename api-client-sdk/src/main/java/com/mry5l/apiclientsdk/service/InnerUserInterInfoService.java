package com.mry5l.apiclientsdk.service;


/**
 * 用户接口服务
 * @author YJL
 * @version 1.0
 */
public interface InnerUserInterInfoService{
    /**
     * 接口次数统计
     * @param interfaceId ...
     * @param userId ...
     * @return ...
     */
    boolean invokeCount(long interfaceId,long userId);

    /**
     * 剩余调用次数
     * @param userId ...
     * @return ...
     */
    boolean invokeResidueCount(long userId);
}
