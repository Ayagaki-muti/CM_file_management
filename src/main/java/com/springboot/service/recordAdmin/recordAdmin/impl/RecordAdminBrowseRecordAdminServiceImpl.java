package com.springboot.service.recordAdmin.recordAdmin.impl;

import com.springboot.dao.RecordAdminDao;
import com.springboot.service.recordAdmin.recordAdmin.RecordAdminBrowseRecordAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecordAdminBrowseRecordAdminServiceImpl implements RecordAdminBrowseRecordAdminService {

    @Autowired
    private RecordAdminDao recordAdminDaoImpl;

    @Override
    public Map<String, Object> recordAdminBrowseRecordAdmin(String recordAdminId) {
        return recordAdminDaoImpl.selectRecordAdminByRecordAdminId(recordAdminId);
    }

}
