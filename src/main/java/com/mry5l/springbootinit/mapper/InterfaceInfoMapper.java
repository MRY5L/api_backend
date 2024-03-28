package com.mry5l.springbootinit.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mry5l.apiclientsdk.model.entity.InterfaceInfo;
import org.apache.ibatis.annotations.Param;

/**
 * @author YJL
 * @description 针对表【interface_info(接口信息)】的数据库操作Mapper
 * @createDate 2024-03-11 19:29:28
 * @Entity generator.domain.InterfaceInfo
 */
public interface InterfaceInfoMapper extends BaseMapper<InterfaceInfo> {

    InterfaceInfo getApiUrl(@Param("apiUrl") String apiUrl, @Param("method") String method);
}




