package com.springboot.service.admin.adminInfo;

import com.springboot.entity.AdminInfo;


public interface AdminUpdateAdminInfoService {
    /**
     * 用户更新
     * 成功：200
     * 找不到用户：401
     * 网络异常：402
     */
    Integer adminUpdateAdminInfo(AdminInfo adminInfo);
}
