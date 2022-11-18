package com.springboot.service.recordAdmin.recordAuth;

import com.springboot.entity.RecordAuth;


public interface RecordAdminUpdateRecordAuthService {
    /**
     * 成功：200
     * 网络异常：402
     */
    Integer adminUpdateRecordAuth(RecordAuth recordAuth);
}
