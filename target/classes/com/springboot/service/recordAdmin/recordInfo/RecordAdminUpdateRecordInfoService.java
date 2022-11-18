package com.springboot.service.recordAdmin.recordInfo;

import com.springboot.entity.RecordInfo;

public interface RecordAdminUpdateRecordInfoService {
    /**
     * 档案更新
     * 成功：200
     * 网络异常：EXECUTE_FAILED_USER_DATA
     */
    Integer adminUpdateRecordInfoByRecordId(RecordInfo recordInfo);
}
