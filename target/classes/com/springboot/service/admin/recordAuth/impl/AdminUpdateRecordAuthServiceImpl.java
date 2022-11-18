package com.springboot.service.admin.recordAuth.impl;

import com.springboot.dao.RecordAuthDao;
import com.springboot.entity.RecordAuth;
import com.springboot.service.admin.recordAuth.AdminUpdateRecordAuthService;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.springboot.config.StaticConfig.*;


@Service
public class AdminUpdateRecordAuthServiceImpl implements AdminUpdateRecordAuthService {

    @Autowired
    private RecordAuthDao recordAuthDaoImpl;

    @Override
    public Integer adminUpdateRecordAuth(RecordAuth recordAuth) {
        if (recordAuthDaoImpl.selectRecordAuthByRecordAuthId(recordAuth.getRecordAuthId()) != null) {
            Slf4j.logger.info("Service:管理员更新档案授权信息" + recordAuth.toString());
            if (recordAuthDaoImpl.insertRecordAuth(recordAuth)) {
                return EXECUTE_SUCCESS_CODE_FRONT;
            } else {
                return EXECUTE_FAILED_NETWORK_CONNECTION;
            }
        } else {
            return EXECUTE_FAILED_USER_DATA;
        }
    }
}
