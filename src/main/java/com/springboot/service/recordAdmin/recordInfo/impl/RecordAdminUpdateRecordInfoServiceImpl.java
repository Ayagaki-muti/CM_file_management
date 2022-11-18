package com.springboot.service.recordAdmin.recordInfo.impl;

import com.springboot.dao.RecordInfoDao;
import com.springboot.entity.RecordInfo;
import com.springboot.service.recordAdmin.recordInfo.RecordAdminUpdateRecordInfoService;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.springboot.config.StaticConfig.*;

@Service
public class RecordAdminUpdateRecordInfoServiceImpl implements RecordAdminUpdateRecordInfoService {

    @Autowired
    private RecordInfoDao recordInfoDaoImpl;

    @Override
    public Integer adminUpdateRecordInfoByRecordId(RecordInfo recordInfo) {
        if (recordInfoDaoImpl.selectRecordInfoByRecordId(recordInfo.getRecordId()) != null) {
            Slf4j.logger.info("Service:管理员更新档案信息" + recordInfo.toString());
            if (recordInfoDaoImpl.insertRecordInfo(recordInfo)) {
                return EXECUTE_SUCCESS_CODE_FRONT;
            } else {
                return EXECUTE_FAILED_NETWORK_CONNECTION;
            }
        } else {
            return EXECUTE_FAILED_USER_DATA;
        }
    }

}
