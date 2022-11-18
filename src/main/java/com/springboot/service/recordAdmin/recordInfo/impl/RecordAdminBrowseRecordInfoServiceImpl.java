package com.springboot.service.recordAdmin.recordInfo.impl;

import com.springboot.dao.KeyValueDao;
import com.springboot.dao.RecordInfoDao;
import com.springboot.entity.KeyValue;
import com.springboot.service.recordAdmin.recordInfo.RecordAdminBrowseRecordInfoService;
import com.springboot.utils.myLog.Slf4j;
import com.springboot.utils.myMap.MapUntils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class RecordAdminBrowseRecordInfoServiceImpl implements RecordAdminBrowseRecordInfoService {

    @Autowired
    private RecordInfoDao recordInfoDaoImpl;
    @Autowired
    private KeyValueDao keyValueDaoImpl;

    @Override
    public Map<String, Object> adminBrowseRecordInfoByRecordId(String recordId) {
        return recordInfoDaoImpl.selectRecordInfoByRecordId(recordId);
    }

    @Override
    public List<Map<String, Object>> adminBrowseAllRecordInfo() {
        try {
            List<Map<String, Object>> recordInfos = keyValueDaoImpl.selectAllKeyIndexByKey("recordInfo");
            Set<Map<String, Object>> allRecordInfo = new HashSet<>();
            for (Map<String, Object> keyValue : recordInfos
            ) {
                KeyValue keyValue1 = new KeyValue();
                MapUntils.mapToBean(keyValue, keyValue1);
                Map<String, Object> recordInfo = recordInfoDaoImpl.selectRecordInfoByRecordId(keyValue1.getValueA());
                Slf4j.logger.info("管理员查看档案信息：" + recordInfo.toString());
                allRecordInfo.add(recordInfo);
            }
            List<Map<String, Object>> result = new ArrayList<>(allRecordInfo);
            // 根据ID升序
            MapUntils.sort(result, "recordId");
            return result;
        } catch (NullPointerException ex) {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> adminBrowseAllRecordInfoByRecordId(String recordId) {
        try {
            List<Map<String, Object>> result = recordInfoDaoImpl.selectAllRecordInfoByRecordId(recordId);
            // 根据tima降序
            //MapUntils.sort(result,"recordTime",false);
            return result;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
}
