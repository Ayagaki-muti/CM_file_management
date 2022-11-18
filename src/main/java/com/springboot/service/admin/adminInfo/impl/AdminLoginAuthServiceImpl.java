package com.springboot.service.admin.adminInfo.impl;

import com.springboot.dao.AdminInfoDao;
import com.springboot.entity.AdminInfo;
import com.springboot.service.admin.adminInfo.AdminLoginAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminLoginAuthServiceImpl implements AdminLoginAuthService {

    @Autowired
    AdminInfoDao adminInfoDaoImpl;

    @Override
    public boolean adminLoginAuth(AdminInfo adminInfo) {
        try {
            return adminInfo.getAdminPassword().equals(adminInfoDaoImpl.selectAdminInfoByAdminId(adminInfo.getAdminId()).get("adminPassword"));//用户输入的密码与数据库对比
        } catch (NullPointerException ex) {
            return false;
        }
    }

}
