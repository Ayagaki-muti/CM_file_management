package com.springboot.service.recordAdmin.recordAdmin.impl;

import com.springboot.dao.RecordAdminDao;
import com.springboot.entity.RecordAdmin;
import com.springboot.service.recordAdmin.recordAdmin.RecordAdminLoginAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RecordAdminLoginAuthServiceImpl implements RecordAdminLoginAuthService {

    @Autowired
    private RecordAdminDao recordAdminDaoImpl;

    @Override
    public boolean recordAdminLoginAuth(RecordAdmin recordAdmin) {
        try {
            return recordAdmin.getRecordAdminPassword().equals(recordAdminDaoImpl.selectRecordAdminByRecordAdminId(recordAdmin.getRecordAdminId()).get("recordAdminPassword"));
        } catch (NullPointerException ex) {
            return false;
        }
    }

}
