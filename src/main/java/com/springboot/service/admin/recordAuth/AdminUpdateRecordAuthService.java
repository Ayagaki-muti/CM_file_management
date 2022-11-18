package com.springboot.service.admin.recordAuth;

import com.springboot.entity.RecordAuth;


public interface AdminUpdateRecordAuthService {
    /**
     * 成功：200
     * 网络异常：402
     */
    Integer adminUpdateRecordAuth(RecordAuth recordAuth);
}
