package com.springboot.service.admin.recordAdmin.impl;

import com.springboot.dao.KeyValueDao;
import com.springboot.dao.RecordAdminDao;
import com.springboot.entity.RecordAdmin;
import com.springboot.service.admin.recordAdmin.AdminInsertAdminService;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.springboot.config.StaticConfig.EXECUTE_FAILED_NETWORK_CONNECTION;
import static com.springboot.config.StaticConfig.INITIAL_VALUE_RECORDADMINID;


@Service
public class AdminInsertAdminServiceImpl implements AdminInsertAdminService {

    @Autowired
    private RecordAdminDao recordAdminDaoImpl;
    @Autowired
    private KeyValueDao keyValueDaoImpl;

    @Override
    public Integer adminInsertRecordAdmin(RecordAdmin recordAdmin) {
        Integer key = null;
        try {
            key = keyValueDaoImpl.getNextChainCodeId("recordAdmin");
            recordAdmin.setRecordAdminId(String.valueOf(key));
        } catch (NullPointerException e) {
            Slf4j.logger.info("当前档案授权记录为第一个");
            key = Integer.valueOf(INITIAL_VALUE_RECORDADMINID);
            recordAdmin.setRecordAdminId(INITIAL_VALUE_RECORDADMINID);
        } finally {
            Slf4j.logger.info("Service:档案管理员注册" + recordAdmin.toString());
            if (recordAdminDaoImpl.insertRecordAdmin(recordAdmin)) {
                return key;
            } else {
                return EXECUTE_FAILED_NETWORK_CONNECTION;
            }
        }

    }
}
