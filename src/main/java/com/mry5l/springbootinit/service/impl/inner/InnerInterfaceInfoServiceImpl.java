package com.mry5l.springbootinit.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mry5l.apiclientsdk.common.ErrorCode;
import com.mry5l.apiclientsdk.exception.BusinessException;
import com.mry5l.apiclientsdk.model.entity.InterfaceInfo;
import com.mry5l.apiclientsdk.service.InnerInterfaceInfoService;
import com.mry5l.springbootinit.mapper.InterfaceInfoMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author YJL
 * @version 1.0
 */
@DubboService
public class InnerInterfaceInfoServiceImpl implements InnerInterfaceInfoService {
    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public InterfaceInfo getInterfaceInfo(String url, String method) {
        if (StringUtils.isAnyBlank(url, method)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return interfaceInfoMapper.getApiUrl(url, method);
    }
}
