package com.mry5l.springbootinit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mry5l.apiclientsdk.model.entity.UserInterfaceInfo;

import java.util.List;

/**
* @author YJL
* @description 针对表【user_interface_info(用户调用接口关系)】的数据库操作Mapper
* @createDate 2024-03-14 18:03:36
* @Entity com.mry5l.springbootinit.model.entity.UserInterfaceInfo
*/
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {

    List<UserInterfaceInfo> getTopInvokerInterfaceInfo(int limit);
}




