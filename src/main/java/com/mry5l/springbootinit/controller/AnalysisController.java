package com.mry5l.springbootinit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mry5l.apiclientsdk.common.BaseResponse;
import com.mry5l.apiclientsdk.common.ErrorCode;
import com.mry5l.apiclientsdk.common.ResultUtils;
import com.mry5l.apiclientsdk.exception.BusinessException;
import com.mry5l.apiclientsdk.model.entity.InterfaceInfo;
import com.mry5l.apiclientsdk.model.entity.UserInterfaceInfo;
import com.mry5l.springbootinit.annotation.AuthCheck;
import com.mry5l.springbootinit.mapper.UserInterfaceInfoMapper;
import com.mry5l.springbootinit.model.vo.InterfaceInfoVO;
import com.mry5l.springbootinit.service.InterfaceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分析控制器
 *
 * @author YJL
 * @version 1.0
 */
@RequestMapping("/analysis")
@RestController
@Slf4j
public class AnalysisController {

    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Resource
    private InterfaceInfoService interfaceInfoService;


    @GetMapping("/top/interface/invoke")
    @AuthCheck(mustRole = "admin")
    public BaseResponse<List<InterfaceInfoVO>> getTopInvokerInterfaceInfo() {
        List<UserInterfaceInfo> userInterfaceInfoList = userInterfaceInfoMapper.getTopInvokerInterfaceInfo(3);
        Map<Long, List<UserInterfaceInfo>> interfaceInfoIdObjMap = userInterfaceInfoList.
                stream().collect(
                Collectors.groupingBy(UserInterfaceInfo::getInterfaceInfoId)
        );
        QueryWrapper<InterfaceInfo> wrapper = new QueryWrapper<>();
        wrapper.in("id", interfaceInfoIdObjMap.keySet());
        List<InterfaceInfo> list = interfaceInfoService.list(wrapper);
        if (CollectionUtils.isEmpty(list)) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<InterfaceInfoVO> interfaceInfoVOS = list.stream().map(interfaceInfo -> {
            InterfaceInfoVO interfaceInfoVO = new InterfaceInfoVO();
            BeanUtils.copyProperties(interfaceInfo, interfaceInfoVO);
            Integer totalNum = interfaceInfoIdObjMap.get(interfaceInfo.getId()).get(0).getTotalNum();
            interfaceInfoVO.setTotalNum(totalNum);
            return interfaceInfoVO;
        }).collect(Collectors.toList());
        return ResultUtils.success(interfaceInfoVOS);
    }
}
