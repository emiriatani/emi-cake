package com.myf.myproject.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Member implements Serializable {
    /**
    * 用户标识列
    */
    private Integer id;

    /**
    * 用户名
    */
    private String memberId;

    /**
    * 登录密码
    */
    private String memberPassword;

    /**
    * 手机号
    */
    private String phone;

    /**
    * 用户状态
    */
    private Byte memberStatus;

    /**
    * 创建时间
    */
    private LocalDateTime createTime;

    /**
    * 创建时ip
    */
    private Integer createIp;

    /**
    * 上次登录时间
    */
    private LocalDateTime loginTime;

    /**
    * 上次登录ip
    */
    private Integer loginIp;

    /**
    * 更新时间
    */
    private LocalDateTime updateTime;

    /**
    * 盐值
    */
    private String salt;

    private static final long serialVersionUID = 1L;
}