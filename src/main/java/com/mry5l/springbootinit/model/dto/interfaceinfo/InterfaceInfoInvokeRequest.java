package com.mry5l.springbootinit.model.dto.interfaceinfo;


import lombok.Data;

/**
 * @author YJL
 * @version 1.0
 */
@Data
public class InterfaceInfoInvokeRequest {
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户请求参数
     */
    private String userRequestParams;


    private static final long serialVersionUID = 1L;
}
