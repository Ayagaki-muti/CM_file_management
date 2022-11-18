package com.springboot.service.admin.recordAdmin;

import java.util.List;
import java.util.Map;


public interface AdminBrowseRecordAdminService {
    /**
     * 查看档案管理员的所有信息
     *
     * @return
     */
    Map<String, Object> adminBrowseRecordAdmin(String recordAdminId);

    /**
     * 查看所有档案管理员信息
     */
    List<Map<String, Object>> adminBrowseAllRecordAdmin();
}
