package com.mry5l.springbootinit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mry5l.apiclientsdk.model.entity.UserInterfaceInfo;

/**
 * @author YJL
 * @description 针对表【interface_info(接口信息)】的数据库操作Service
 * @createDate 2024-03-11 19:29:28
 */

public interface UserInterfaceInfoService extends IService<UserInterfaceInfo> {


    void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add);

    /**
     * 统计接口调用次数
     * @param interfaceId
     * @param userId
     * @return
     */
    boolean invokeCount(long interfaceId, long userId);


    /**
     * 剩余调用次数
     * @param userId
     * @return
     */
    boolean invokeResidueCount(long userId);

    /**
     * 添加用户调用接口记录
     * @param userId 用户id
     * @param interfaceInfoId 接口id
     * @return
     */
    boolean addUserInvokeInterface(long userId,long interfaceInfoId);
}
