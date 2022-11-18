package com.springboot.service.admin.recordInfo;

import com.springboot.entity.RecordInfo;


public interface AdminInsertRecordInfoService {
    /**
     * 用户注册
     * 成功：200
     * 档案已存在：401
     * 网络异常：402
     */
    Integer adminInsertRecordInfoByRecordId(RecordInfo recordInfo);
}
