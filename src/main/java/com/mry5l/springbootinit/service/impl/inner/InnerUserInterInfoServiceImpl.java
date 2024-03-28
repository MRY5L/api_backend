package com.mry5l.springbootinit.service.impl.inner;

import com.mry5l.apiclientsdk.service.InnerUserInterInfoService;
import com.mry5l.springbootinit.service.UserInterfaceInfoService;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author YJL
 * @version 1.0
 */
@DubboService
public class InnerUserInterInfoServiceImpl implements InnerUserInterInfoService {
    @Resource
    UserInterfaceInfoService userInterfaceInfoService;

    @Override
    public boolean invokeCount(long interfaceId, long userId) {
        return userInterfaceInfoService.invokeCount(interfaceId, userId);
    }

    @Override
    public boolean invokeResidueCount(long userId) {
        return userInterfaceInfoService.invokeResidueCount(userId);
    }
}
