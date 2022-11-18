package com.springboot.service.user.recordAuth.impl;

import com.springboot.dao.KeyValueDao;
import com.springboot.dao.RecordAuthDao;
import com.springboot.entity.KeyValue;
import com.springboot.entity.RecordAuth;
import com.springboot.service.user.recordAuth.UserBrowseRecordAuthService;
import com.springboot.utils.myDate.DealDate;
import com.springboot.utils.myLog.Slf4j;
import com.springboot.utils.myMap.MapUntils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserBrowseRecordAuthServiceImpl implements UserBrowseRecordAuthService {

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
    public List<Map<String, Object>> userBrowseAllRecordAuthByUserId(String userId) {
        try {
            // 获取所有的授权信息
            List<Map<String, Object>> recordAuths = keyValueDaoImpl.selectAllKeyIndexByKey("recordAuth");
            Set<Map<String, Object>> allRecordAuth = new HashSet<>();
            for (Map<String, Object> keyValue : recordAuths
            ) {
                KeyValue keyValue1 = new KeyValue();
                System.out.println("获取该用户的所有授权ID" + keyValue.toString());
                MapUntils.mapToBean(keyValue, keyValue1);
                Map<String, Object> recordAuth = recordAuthDaoImpl.selectRecordAuthByRecordAuthId(keyValue1.getValueA());
                Slf4j.logger.info("用户查看档案授权信息：" + recordAuth.toString());
                /**
                 * 因为keyValue中保存的是最新授权和所有历史授权
                 * 所以要判断keyvalue中的结果集中的档案授权有没有已经更改授权的情况
                 *  如果最新授权还是给了这位用户
                 *      那么判断档案授权是否超过了授权截止时间
                 *          若没超过把结果加进去
                 * */
                RecordAuth recordAuth1 = new RecordAuth();
                MapUntils.mapToBean(recordAuth, recordAuth1);
                // 如果不是授权给自己的那么略过
                if(!recordAuth1.getRecordUserId().equals(userId)){
                    continue;
                }
                if (userId.equals(recordAuth1.getRecordUserId())) {
                    Date cutOffTime = DealDate.handleStringToDate2(recordAuth1.getRecordAuthCutOffTime());
                    Date nowData = new Date();
                    if (cutOffTime.compareTo(nowData) == 1) {
                        allRecordAuth.add(recordAuth);
                    }
                }
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
