package com.mry5l.springbootinit.model.vo;

import com.mry5l.apiclientsdk.model.entity.InterfaceInfo;
import lombok.Data;

/**
 * 接口信息封装视图
 * @author YJL
 * @version 1.0
 */
@Data
public class InterfaceInfoVO extends InterfaceInfo {
    /**
     * 调用次数
     */
    private Integer totalNum;

    private static final long serialVersionUID = 1L;
}
