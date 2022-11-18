package com.springboot.service.admin.adminInfo;

import com.springboot.entity.AdminInfo;


public interface AdminInsertAdminInfoService {
    /**
     * 用户注册
     * 成功：200
     * 已有该用户：401
     * 网络异常：EXECUTE_FAILED_USER_DATA
     */
    Integer adminInsertAdminInfo(AdminInfo adminInfo);
}
