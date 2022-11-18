package com.springboot.service.recordAdmin.recordAdmin.impl;

import com.springboot.dao.RecordAdminDao;
import com.springboot.entity.RecordAdmin;
import com.springboot.service.recordAdmin.recordAdmin.RecordAdminUpdateRecordAdminService;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.springboot.config.StaticConfig.*;


@Service
public class RecordAdminUpdateRecordAdminServiceImpl implements RecordAdminUpdateRecordAdminService {

    @Autowired
    private RecordAdminDao recordAdminDaoImpl;

    @Override
    public Integer RecordAdminUpdateRecordAdmin(RecordAdmin recordAdmin) {
        if (recordAdminDaoImpl.selectRecordAdminByRecordAdminId(recordAdmin.getRecordAdminId()) != null) {
            Slf4j.logger.info("Service:档案管理员更新" + recordAdmin.toString());
            if (recordAdminDaoImpl.insertRecordAdmin(recordAdmin)) {
                return EXECUTE_SUCCESS_CODE_FRONT;
            } else {
                return EXECUTE_FAILED_NETWORK_CONNECTION;
            }
        } else {
            return EXECUTE_FAILED_USER_DATA;
        }
    }
}
