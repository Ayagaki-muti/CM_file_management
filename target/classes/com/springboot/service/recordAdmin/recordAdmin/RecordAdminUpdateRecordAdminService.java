package com.springboot.service.recordAdmin.recordAdmin;

import com.springboot.entity.RecordAdmin;


public interface RecordAdminUpdateRecordAdminService {
    /**
     * 用户更新
     * 成功：200
     * 找不到用户：401
     * 网络异常：402
     */
    Integer RecordAdminUpdateRecordAdmin(RecordAdmin recordAdmin);
}
