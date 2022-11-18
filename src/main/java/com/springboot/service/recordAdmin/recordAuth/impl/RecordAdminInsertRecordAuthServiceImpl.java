package com.springboot.service.recordAdmin.recordAuth.impl;

import com.springboot.dao.KeyValueDao;
import com.springboot.dao.RecordAuthDao;
import com.springboot.entity.RecordAuth;
import com.springboot.service.recordAdmin.recordAuth.RecordAdminInsertRecordAuthService;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.springboot.config.StaticConfig.EXECUTE_FAILED_NETWORK_CONNECTION;
import static com.springboot.config.StaticConfig.INITIAL_VALUE_RECORDAUTHID;


@Service
public class RecordAdminInsertRecordAuthServiceImpl implements RecordAdminInsertRecordAuthService {

    @Autowired
    private RecordAuthDao recordAuthDaoImpl;
    @Autowired
    private KeyValueDao keyValueDaoImpl;

    @Override
    public Integer adminInsertRecordAuth(RecordAuth recordAuth) {
        /*if(keyValueDaoImpl.selectRecordAuthByUserId2RecordId(recordAuth.getRecordUserId(),recordAuth.getRecordId())==null){
            return EXECUTE_FAILED_USER_DATA;
        }*/
        Integer key = null;
        try {
            key = keyValueDaoImpl.getNextChainCodeId("recordAuth");
            System.out.println(key);
            recordAuth.setRecordAuthId(String.valueOf(key));
        } catch (NullPointerException e) {
            Slf4j.logger.info("当前档案授权记录为第一个");
            key = Integer.valueOf(INITIAL_VALUE_RECORDAUTHID);
            recordAuth.setRecordAuthId(INITIAL_VALUE_RECORDAUTHID);
        } finally {
            Slf4j.logger.info("Service:管理员添加档案授权信息" + recordAuth.toString());
            if (recordAuthDaoImpl.insertRecordAuth(recordAuth)) {
                return key;
            } else {
                return EXECUTE_FAILED_NETWORK_CONNECTION;
            }
        }

    }
}
