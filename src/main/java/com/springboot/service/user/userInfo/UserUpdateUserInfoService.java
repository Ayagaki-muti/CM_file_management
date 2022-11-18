package com.springboot.service.user.userInfo;

import com.springboot.entity.UserInfo;

public interface UserUpdateUserInfoService {
    /**
     * 用户更新
     * 成功：200
     * 找不到用户：401
     * 网络异常：402
     */
    Integer adminUpdateUserInfo(UserInfo userInfo);
}
