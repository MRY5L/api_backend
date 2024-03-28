package com.mry5l.apiclientsdk.service;


import com.mry5l.apiclientsdk.model.entity.InterfaceInfo;

/**
 * 接口服务
 * @author YJL
 * @version 1.0
*/

public interface InnerInterfaceInfoService {


    /**
     * 从数据库中查询模拟接口是否存在
     * @param path ...
     * @param method ...
     * @return ...
     */
    InterfaceInfo getInterfaceInfo(String path, String method);
}
