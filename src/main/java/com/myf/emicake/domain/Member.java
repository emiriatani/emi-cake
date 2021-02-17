package com.myf.emicake.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
    * 创建时ip
    */
    private Integer createIp;

    /**
    * 上次登录时间
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;

    /**
    * 上次登录ip
    */
    private Integer loginIp;

    /**
    * 更新时间
    */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
    * 盐值
    */
    private String salt;

    private static final long serialVersionUID = 1L;
}