package com.mry5l.springbootinit.model.dto.user;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户更新请求
 */
@Data
public class UserUpdateRequest implements Serializable {
    /**
     * id
     */
    private Long id;

    private String userAccount;


    private static final long serialVersionUID = 1L;
}