package com.springboot.service.admin.adminInfo;

import com.springboot.entity.AdminInfo;


public interface AdminLoginAuthService {

    /**
     * 登录验证
     *
     * @param adminInfo:AdminInfo对象 内含ID和密码用于验证是否可以登陆
     * @return boolean
     */
    boolean adminLoginAuth(AdminInfo adminInfo);
}
