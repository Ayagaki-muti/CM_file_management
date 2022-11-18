package com.springboot.service.admin.recordAdmin;

import com.springboot.entity.RecordAdmin;


public interface AdminUpdateRecordAdminService {
    /**
     * 用户更新
     * 成功：200
     * 找不到用户：401
     * 网络异常：402
     */
    Integer adminUpdateRecordAdmin(RecordAdmin recordAdmin);
}
