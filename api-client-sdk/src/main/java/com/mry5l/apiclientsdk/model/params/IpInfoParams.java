package com.mry5l.apiclientsdk.model.params;

import lombok.Data;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * 请求参数类
 * @author YJL
 * @version 1.0
 */
@Data
public class IpInfoParams implements Serializable {
    /**
     * ip地址
     */
    private String ip;

    private static final long serialVersionUID = 1L;
}
