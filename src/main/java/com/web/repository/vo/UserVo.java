package com.web.repository.vo;

import lombok.Data;

@Data
public class UserVo {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 账号
     */
    private String account;

    /**
     * 头像
     */
    private String userIcon;
}
