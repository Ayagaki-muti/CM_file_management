package com.springboot.service.recordAdmin.recordAuth;

import com.springboot.entity.RecordAuth;


public interface RecordAdminInsertRecordAuthService {
    /**
     * 成功：200
     * 该档案已经授权给该用户了：EXECUTE_FAILED_USER_DATA
     * 网络异常：402
     */
    Integer adminInsertRecordAuth(RecordAuth recordAuth);
}
