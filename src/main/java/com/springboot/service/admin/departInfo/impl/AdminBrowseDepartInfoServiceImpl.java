package com.springboot.service.admin.departInfo.impl;

import com.springboot.dao.DepartInfoDao;
import com.springboot.dao.KeyValueDao;
import com.springboot.entity.KeyValue;
import com.springboot.service.admin.departInfo.AdminBrowseDepartInfoService;
import com.springboot.utils.myLog.Slf4j;
import com.springboot.utils.myMap.MapUntils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class AdminBrowseDepartInfoServiceImpl implements AdminBrowseDepartInfoService {

    @Autowired
    private DepartInfoDao departInfoDaoImpl;
    @Autowired
    private KeyValueDao keyValueDaoImpl;

    @Override
    public Map<String, Object> adminBrowseDepartInfo(String departId) {
        return departInfoDaoImpl.selectDepartInfoByDepartId(departId);
    }

    @Override
    public List<Map<String, Object>> adminBrowseAllDepartInfo() {
        try {
            List<Map<String, Object>> departInfos = keyValueDaoImpl.selectAllKeyIndexByKey("departInfo");
            List<Map<String, Object>> allRecordAdmin = new ArrayList<>();
            for (Map<String, Object> keyValue : departInfos) {
                KeyValue keyValue1 = new KeyValue();
                MapUntils.mapToBean(keyValue, keyValue1);
                Map<String, Object> departInfo = departInfoDaoImpl.selectDepartInfoByDepartId(keyValue1.getValueA());
                Slf4j.logger.info("管理员查看部门信息：" + departInfo.toString());
                allRecordAdmin.add(departInfo);
            }
            // 按着ID升序
            MapUntils.sort(allRecordAdmin, "departId");
            return allRecordAdmin;
        } catch (NullPointerException ex) {
            return null;
        }
    }


}
