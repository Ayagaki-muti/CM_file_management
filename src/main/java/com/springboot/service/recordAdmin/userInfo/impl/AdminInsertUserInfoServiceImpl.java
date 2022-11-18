package com.springboot.service.recordAdmin.userInfo.impl;

import com.springboot.dao.KeyValueDao;
import com.springboot.dao.UserInfoDao;
import com.springboot.entity.UserInfo;
import com.springboot.service.recordAdmin.userInfo.AdminInsertUserInfoService;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.springboot.config.StaticConfig.EXECUTE_FAILED_NETWORK_CONNECTION;
import static com.springboot.config.StaticConfig.INITIAL_VALUE_USERID;

@Service
public class AdminInsertUserInfoServiceImpl implements AdminInsertUserInfoService {

    @Autowired
    private UserInfoDao userInfoDaoImpl;
    @Autowired
    private KeyValueDao keyValueDaoImpl;

    @Override
    public Integer adminRegisterUserInfo(UserInfo userInfo) {
        Integer key = null;
        try {
            key = keyValueDaoImpl.getNextChainCodeId("userInfo");
            userInfo.setUserId(String.valueOf(key));
        } catch (NullPointerException e) {
            Slf4j.logger.info("当前用户记录为第一个");
            key = Integer.valueOf(INITIAL_VALUE_USERID);
            userInfo.setUserId(INITIAL_VALUE_USERID);
        } finally {
            Slf4j.logger.info("Service:管理员注册用户" + userInfo.toString());
            if (userInfoDaoImpl.insertUserInfo(userInfo)) {
                return key;
            } else {
                return EXECUTE_FAILED_NETWORK_CONNECTION;
            }
        }
    }
}
