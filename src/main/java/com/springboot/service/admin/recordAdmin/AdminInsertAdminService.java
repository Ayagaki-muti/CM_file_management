package com.springboot.service.admin.recordAdmin;

import com.springboot.entity.RecordAdmin;


public interface AdminInsertAdminService {
    /**
     * 用户注册
     * 成功：200
     * 已有该用户：401
     * 网络异常：402
     */
    Integer adminInsertRecordAdmin(RecordAdmin recordAdmin);
}
