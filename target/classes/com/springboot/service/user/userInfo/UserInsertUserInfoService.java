package com.springboot.service.user.userInfo;

import com.springboot.entity.UserInfo;


public interface UserInsertUserInfoService {
    /**
     * 用户注册
     * 成功：200
     * 已有该用户：401
     * 网络异常：EXECUTE_FAILED_USER_DATA
     */
    Integer userRegister(UserInfo userInfo);
}
