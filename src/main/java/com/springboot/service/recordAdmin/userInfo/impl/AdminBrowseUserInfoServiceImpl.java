package com.springboot.service.recordAdmin.userInfo.impl;

import com.springboot.dao.KeyValueDao;
import com.springboot.dao.UserInfoDao;
import com.springboot.entity.KeyValue;
import com.springboot.service.recordAdmin.userInfo.AdminBrowseUserInfoService;
import com.springboot.utils.myLog.Slf4j;
import com.springboot.utils.myMap.MapUntils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class AdminBrowseUserInfoServiceImpl implements AdminBrowseUserInfoService {

    @Autowired
    private UserInfoDao userInfoDaoImpl;
    @Autowired
    private KeyValueDao keyValueDaoImpl;

    @Override
    public Map<String, Object> adminBrowseUserInfoByUserId(String userId) {
        return userInfoDaoImpl.selectUserInfoByUserId(userId);
    }

    @Override
    public List<Map<String, Object>> adminBrowseAllUserInfo() {
        try {
            List<Map<String, Object>> recordAdmins = keyValueDaoImpl.selectAllKeyIndexByKey("userInfo");
            List<Map<String, Object>> allUserInfo = new ArrayList<>();
            for (Map<String, Object> keyValue : recordAdmins
            ) {
                KeyValue keyValue1 = new KeyValue();
                MapUntils.mapToBean(keyValue, keyValue1);
                Map<String, Object> userInfo = userInfoDaoImpl.selectUserInfoByUserId(keyValue1.getValueA());
                Slf4j.logger.info("管理员查看用户信息：" + userInfo.toString());
                allUserInfo.add(userInfo);
            }
            // 按着ID升序
            MapUntils.sort(allUserInfo, "userId");
            return allUserInfo;
        } catch (NullPointerException ex) {
            return null;
        }
    }


}
