package com.springboot.service.user.userInfo;

import com.springboot.entity.UserInfo;

public interface UserLoginAuthService {

    /**
     * 登录验证
     *
     * @param login:User对象 内含ID和密码用于验证是否可以登陆
     * @return boolean
     */
    boolean userLoginAuth(UserInfo login);
}
