package com.springboot.service.user.recordInfo.impl;

import com.springboot.dao.KeyValueDao;
import com.springboot.dao.RecordInfoDao;
import com.springboot.service.user.recordInfo.UserBrowseRecordInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class UserBrowseRecordInfoServiceImpl implements UserBrowseRecordInfoService {

    @Autowired
    private RecordInfoDao recordInfoDaoImpl;
    @Autowired
    private KeyValueDao keyValueDaoImpl;

    @Override
    public Map<String, Object> userBrowseRecordInfoByRecordId(String recordId) {
        return recordInfoDaoImpl.selectRecordInfoByRecordId(recordId);
    }

    @Override
    public List<Map<String, Object>> userBrowseAllRecordInfoByRecordId(String recordId) {
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
