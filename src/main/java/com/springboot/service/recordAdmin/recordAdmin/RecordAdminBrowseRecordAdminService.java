package com.springboot.service.recordAdmin.recordAdmin;

import java.util.Map;


public interface RecordAdminBrowseRecordAdminService {
    /**
     * 查看档案管理员的所有信息
     *
     * @return
     */
    Map<String, Object> recordAdminBrowseRecordAdmin(String recordAdminId);

}
