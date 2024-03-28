package com.mry5l.springbootinit.service.impl.inner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mry5l.apiclientsdk.common.ErrorCode;
import com.mry5l.apiclientsdk.exception.BusinessException;
import com.mry5l.apiclientsdk.model.entity.User;
import com.mry5l.apiclientsdk.service.InnerUserService;
import com.mry5l.springbootinit.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

/**
 * @author YJL
 * @version 1.0
 */
@DubboService
public class InnerUserServiceImpl implements InnerUserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User getInvokeUser(String accessKey) {
        if (StringUtils.isAnyBlank(accessKey)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("accessKey", accessKey);
        User user = userMapper.selectOne(userQueryWrapper);
        return user;
    }
}
