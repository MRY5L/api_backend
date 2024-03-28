package com.mry5l.springbootinit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mry5l.apiclientsdk.model.entity.InterfaceInfo;

/**
* @author YJL
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-03-11 19:29:28
*/

public interface InterfaceInfoService extends IService<InterfaceInfo> {


    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);
}
