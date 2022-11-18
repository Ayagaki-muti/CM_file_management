package com.springboot.service.recordAdmin.userInfo;

import com.springboot.entity.UserInfo;


public interface AdminInsertUserInfoService {
    /**
     * 用户注册
     * 成功：200
     * 已有该用户：401
     * 网络异常：402
     */
    Integer adminRegisterUserInfo(UserInfo userInfo);
}
