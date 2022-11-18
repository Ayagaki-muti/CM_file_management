package com.springboot.service.admin.recordInfo.impl;

import com.springboot.dao.KeyValueDao;
import com.springboot.dao.RecordInfoDao;
import com.springboot.entity.RecordInfo;
import com.springboot.service.admin.recordInfo.AdminInsertRecordInfoService;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.springboot.config.StaticConfig.*;


@Service
public class AdminInsertRecordInfoServiceImpl implements AdminInsertRecordInfoService {

    @Autowired
    private RecordInfoDao recordInfoDaoImpl;
    @Autowired
    private KeyValueDao keyValueDaoImpl;

    @Override
    public Integer adminInsertRecordInfoByRecordId(RecordInfo recordInfo) {
        Integer key = null;
        try {
            key = keyValueDaoImpl.getNextChainCodeId("recordInfo");//找下一个id
            recordInfo.setRecordId(String.valueOf(key));
        } catch (NullPointerException e) {
            Slf4j.logger.info("当前档案记录为第一个");
            key = Integer.valueOf(INITIAL_VALUE_RECORDID);
            recordInfo.setRecordId(INITIAL_VALUE_RECORDID);
        } finally {
            Slf4j.logger.info("Service:管理员添加档案信息" + recordInfo.toString());
            if (recordInfoDaoImpl.insertRecordInfo(recordInfo)) {
                return key;
            } else {
                return EXECUTE_FAILED_NETWORK_CONNECTION;
            }
        }
    }

}
