package com.springboot.service.recordAdmin.recordAuth;

import java.util.List;
import java.util.Map;


public interface RecordAdminBrowseRecordAuthService {
    /**
     * 查看指定id的档案授权信息
     */
    Map<String, Object> adminBrowseRecordAuthByRecordAuthId(String recordAuthId);

    /**
     * 查看指定id的档案授权信息
     */
    List<Map<String, Object>> adminBrowseAllRecordAuthByRecordAuthId(String recordAuthId);

    /**
     * 查看所有档案授权
     */
    List<Map<String, Object>> adminBrowseAllRecordAuth();
}
