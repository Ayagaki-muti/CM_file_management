package com.springboot.service.recordAdmin.recordAuth.impl;

import com.springboot.dao.KeyValueDao;
import com.springboot.dao.RecordAuthDao;
import com.springboot.entity.KeyValue;
import com.springboot.service.recordAdmin.recordAuth.RecordAdminBrowseRecordAuthService;
import com.springboot.utils.myLog.Slf4j;
import com.springboot.utils.myMap.MapUntils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class RecordAdminBrowseRecordAuthServiceImpl implements RecordAdminBrowseRecordAuthService {

    @Autowired
    private RecordAuthDao recordAuthDaoImpl;
    @Autowired
    private KeyValueDao keyValueDaoImpl;

    @Override
    public Map<String, Object> adminBrowseRecordAuthByRecordAuthId(String recordAuthId) {
        return recordAuthDaoImpl.selectRecordAuthByRecordAuthId(recordAuthId);
    }

    /**
     * 查看指定id的档案授权信息
     */
    @Override
    public List<Map<String, Object>> adminBrowseAllRecordAuthByRecordAuthId(String recordAuthId) {
        List<Map<String, Object>> result = recordAuthDaoImpl.selectAllRecordAuthByRecordAuthId(recordAuthId);
        // 时间倒叙排序
        MapUntils.sort(result, "recordAuthTime", false);
        return result;
    }

    @Override
    public List<Map<String, Object>> adminBrowseAllRecordAuth() {
        try {
            List<Map<String, Object>> recordAuths = keyValueDaoImpl.selectAllKeyIndexByKey("recordAuth");
            Set<Map<String, Object>> allRecordAuth = new HashSet<>();
            for (Map<String, Object> keyValue : recordAuths
            ) {
                KeyValue keyValue1 = new KeyValue();
                MapUntils.mapToBean(keyValue, keyValue1);
                Map<String, Object> recordAuth = recordAuthDaoImpl.selectRecordAuthByRecordAuthId(keyValue1.getValueA());
                Slf4j.logger.info("管理员查看档案授权信息：" + recordAuth.toString());
                allRecordAuth.add(recordAuth);
            }
            List<Map<String, Object>> result = new ArrayList<>(allRecordAuth);
            // 按着ID升序
            MapUntils.sort(result, "recordAuthId");
            return result;
        } catch (NullPointerException ex) {
            return null;
        }
    }


}
