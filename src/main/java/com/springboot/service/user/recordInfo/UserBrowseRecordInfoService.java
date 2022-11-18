package com.springboot.service.user.recordInfo;

import java.util.List;
import java.util.Map;


public interface UserBrowseRecordInfoService {
    /**
     * 查看指定id的档案信息
     *
     * @return
     */
    Map<String, Object> userBrowseRecordInfoByRecordId(String recordId);

    /**
     * 查看指定档案的所有信息
     */
    List<Map<String, Object>> userBrowseAllRecordInfoByRecordId(String recordId);

}
