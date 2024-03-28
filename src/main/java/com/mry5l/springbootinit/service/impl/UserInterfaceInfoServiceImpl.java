package com.mry5l.springbootinit.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mry5l.apiclientsdk.common.ErrorCode;
import com.mry5l.apiclientsdk.exception.BusinessException;
import com.mry5l.apiclientsdk.model.entity.User;
import com.mry5l.apiclientsdk.model.entity.UserInterfaceInfo;
import com.mry5l.springbootinit.mapper.UserInterfaceInfoMapper;
import com.mry5l.springbootinit.mapper.UserMapper;
import com.mry5l.springbootinit.service.UserInterfaceInfoService;
import com.mry5l.springbootinit.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author YJL
 * @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Service实现
 * @createDate 2024-03-14 18:03:36
 */
@Service
public class UserInterfaceInfoServiceImpl extends ServiceImpl<UserInterfaceInfoMapper, UserInterfaceInfo>
        implements UserInterfaceInfoService {
    @Resource
    UserMapper userMapper;

    @Override
    public void validUserInterfaceInfo(UserInterfaceInfo userInterfaceInfo, boolean add) {
        if (userInterfaceInfo == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 创建时，所有参数必须非空
        if (add) {
            if (userInterfaceInfo.getInterfaceInfoId() <= 0 || userInterfaceInfo.getUserId() <= 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口或用户不存在");
            }
        }
    }

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        //校验
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口或用户不存在");
        }
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId", interfaceInfoId);
        updateWrapper.eq("userId", userId);
        updateWrapper.setSql("totalNum = totalNum + 1 ");
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", userId);
        wrapper.setSql("leftNum = leftNum - 1");
        userMapper.update(new User(), wrapper);
        boolean update = this.update(updateWrapper);
        return update;
    }

    @Override
    public boolean invokeResidueCount(long userId) {
        //校验
        if (userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        User user = userMapper.selectById(userId);
        if (!(user.getLeftNum() > 0)) {
            return false;
        }
        return true;
    }

    @Override
    public boolean addUserInvokeInterface(long userId, long interfaceInfoId) {
        if (interfaceInfoId <= 0 || userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口或用户不存在");
        }
        QueryWrapper<UserInterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId", userId);
        queryWrapper.eq("interfaceInfoId", interfaceInfoId);
        UserInterfaceInfo one = this.getOne(queryWrapper);
        if (one != null) {
            return true;
        }
        UserInterfaceInfo userInterfaceInfo = new UserInterfaceInfo();
        userInterfaceInfo.setUserId(userId);
        userInterfaceInfo.setInterfaceInfoId(interfaceInfoId);
        boolean save = this.save(userInterfaceInfo);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "添加用户接口信息失败");
        }
        return save;
    }
}




