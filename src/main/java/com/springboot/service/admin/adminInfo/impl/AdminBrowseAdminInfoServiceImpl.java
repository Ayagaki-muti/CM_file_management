package com.springboot.service.admin.adminInfo.impl;

import com.springboot.dao.AdminInfoDao;
import com.springboot.dao.KeyValueDao;
import com.springboot.entity.KeyValue;
import com.springboot.service.admin.adminInfo.AdminBrowseAdminInfoService;
import com.springboot.utils.myLog.Slf4j;
import com.springboot.utils.myMap.MapUntils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class AdminBrowseAdminInfoServiceImpl implements AdminBrowseAdminInfoService {

    @Autowired
    private AdminInfoDao adminInfoDaoImpl;
    @Autowired
    private KeyValueDao keyValueDaoImpl;

    @Override
    public Map<String, Object> adminBrowseAdminInfo(String adminId) {
        return adminInfoDaoImpl.selectAdminInfoByAdminId(adminId);
    }

    @Override
    public List<Map<String, Object>> adminBrowseAllAdminInfo() {
        try {
            List<Map<String, Object>> departInfos = keyValueDaoImpl.selectAllKeyIndexByKey("adminInfo");
            Set<Map<String, Object>> allRecordAdmin = new HashSet<>();
            for (Map<String, Object> keyValue : departInfos) {
                KeyValue keyValue1 = new KeyValue();
                MapUntils.mapToBean(keyValue, keyValue1);
                Map<String, Object> adminInfo = adminInfoDaoImpl.selectAdminInfoByAdminId(keyValue1.getValueA());
                Slf4j.logger.info("管理员查看管理员信息：" + adminInfo.toString());
                allRecordAdmin.add(adminInfo);
            }
            // 按着ID升序
            List<Map<String, Object>> allRecordAdminSet = new ArrayList<>(allRecordAdmin);
            MapUntils.sort(allRecordAdminSet, "adminId");
            return allRecordAdminSet;
        } catch (NullPointerException ex) {
            return null;
        }
    }
}
