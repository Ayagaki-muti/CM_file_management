package com.springboot.service.admin.adminInfo;

import java.util.List;
import java.util.Map;


public interface AdminBrowseAdminInfoService {
    /**
     * 查看管理员的所有信息
     */
    Map<String, Object> adminBrowseAdminInfo(String adminId);

    /**
     * 查看所有管理员的所有信息
     */
    List<Map<String, Object>> adminBrowseAllAdminInfo();
}
