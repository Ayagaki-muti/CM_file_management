package com.springboot.service.admin.adminInfo.impl;

import com.springboot.dao.AdminInfoDao;
import com.springboot.dao.KeyValueDao;
import com.springboot.entity.AdminInfo;
import com.springboot.service.admin.adminInfo.AdminInsertAdminInfoService;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.springboot.config.StaticConfig.EXECUTE_FAILED_NETWORK_CONNECTION;
import static com.springboot.config.StaticConfig.INITIAL_VALUE_ADMINID;


@Service
public class AdminInsertAdminInfoServiceImpl implements AdminInsertAdminInfoService {

    @Autowired
    private AdminInfoDao adminInfoDaoImpl;
    @Autowired
    private KeyValueDao keyValueDaoImpl;

    @Override
    public Integer adminInsertAdminInfo(AdminInfo adminInfo) {
        Integer key = null;
        try {
            key = keyValueDaoImpl.getNextChainCodeId("adminInfo");
            adminInfo.setAdminId(String.valueOf(key));
        } catch (NullPointerException e) {
            Slf4j.logger.info("当前管理员为第一个");
            key = Integer.valueOf(INITIAL_VALUE_ADMINID);
            adminInfo.setAdminId(INITIAL_VALUE_ADMINID);//赋值账号初始值
        } finally {
            Slf4j.logger.info("Service:管理员注册" + adminInfo.toString());
            if (adminInfoDaoImpl.insertAdminInfo(adminInfo)) {//操作数据库
                return key;
            } else {
                return EXECUTE_FAILED_NETWORK_CONNECTION;
            }
        }
    }
}
