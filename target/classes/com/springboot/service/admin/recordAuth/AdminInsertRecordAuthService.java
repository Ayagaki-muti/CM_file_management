package com.springboot.service.admin.recordAuth;

import com.springboot.entity.RecordAuth;


public interface AdminInsertRecordAuthService {
    /**
     * 成功：200
     * 该档案已经授权给该用户：EXECUTE_FAILED_USER_DATA
     * 网络异常：402
     */
    Integer adminInsertRecordAuth(RecordAuth recordAuth);
}
