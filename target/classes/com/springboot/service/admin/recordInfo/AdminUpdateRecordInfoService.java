package com.springboot.service.admin.recordInfo;

import com.springboot.entity.RecordInfo;


public interface AdminUpdateRecordInfoService {
    /**
     * 档案更新
     * 成功：200
     * 网络异常：EXECUTE_FAILED_USER_DATA
     */
    Integer adminUpdateRecordInfoByRecordId(RecordInfo recordInfo);
}
