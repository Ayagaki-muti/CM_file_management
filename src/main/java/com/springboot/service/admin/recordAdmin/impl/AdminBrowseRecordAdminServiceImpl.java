package com.springboot.service.admin.recordAdmin.impl;

import com.springboot.dao.KeyValueDao;
import com.springboot.dao.RecordAdminDao;
import com.springboot.entity.KeyValue;
import com.springboot.service.admin.recordAdmin.AdminBrowseRecordAdminService;
import com.springboot.utils.myLog.Slf4j;
import com.springboot.utils.myMap.MapUntils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class AdminBrowseRecordAdminServiceImpl implements AdminBrowseRecordAdminService {

    @Autowired
    private RecordAdminDao recordAdminDaoImpl;
    @Autowired
    private KeyValueDao keyValueDaoImpl;

    @Override
    public Map<String, Object> adminBrowseRecordAdmin(String recordAdminId) {
        return recordAdminDaoImpl.selectRecordAdminByRecordAdminId(recordAdminId);
    }

    @Override
    public List<Map<String, Object>> adminBrowseAllRecordAdmin() {
        try {
            List<Map<String, Object>> recordAdmins = keyValueDaoImpl.selectAllKeyIndexByKey("recordAdmin");
            List<Map<String, Object>> allRecordAdmin = new ArrayList<>();
            for (Map<String, Object> keyValue : recordAdmins
            ) {
                KeyValue keyValue1 = new KeyValue();
                MapUntils.mapToBean(keyValue, keyValue1);
                Map<String, Object> recordAdmin = recordAdminDaoImpl.selectRecordAdminByRecordAdminId(keyValue1.getValueA());
                Slf4j.logger.info("管理员查看档案管理员信息：" + recordAdmin.toString());
                allRecordAdmin.add(recordAdmin);
            }
            // 按着ID升序
            MapUntils.sort(allRecordAdmin, "recordId");
            return allRecordAdmin;
        } catch (NullPointerException ex) {
            return null;
        }
    }


}
