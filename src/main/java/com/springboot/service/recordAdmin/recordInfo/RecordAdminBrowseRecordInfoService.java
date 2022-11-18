package com.springboot.service.recordAdmin.recordInfo;

import java.util.List;
import java.util.Map;


public interface RecordAdminBrowseRecordInfoService {
    /**
     * 查看指定id的档案信息
     *
     * @return
     */
    Map<String, Object> adminBrowseRecordInfoByRecordId(String recordId);

    /**
     * 查看所有最新档案信息
     *
     * @return
     */
    List<Map<String, Object>> adminBrowseAllRecordInfo();

    /**
     * 查看指定档案的所有信息
     */
    List<Map<String, Object>> adminBrowseAllRecordInfoByRecordId(String recordId);

}
