package com.springboot.service.user.userInfo.impl;

import com.springboot.dao.UserInfoDao;
import com.springboot.entity.UserInfo;
import com.springboot.service.user.userInfo.UserUpdateUserInfoService;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.springboot.config.StaticConfig.*;


@Service
public class UserUpdateUserInfoServiceImpl implements UserUpdateUserInfoService {

    @Autowired
    private UserInfoDao userInfoDaoImpl;

    @Override
    public Integer adminUpdateUserInfo(UserInfo userInfo) {
        if (userInfoDaoImpl.selectUserInfoByUserId(userInfo.getUserId()) != null) {
            Slf4j.logger.info("Service:管理员更新用户信息" + userInfo.toString());
            if (userInfoDaoImpl.insertUserInfo(userInfo)) {
                return EXECUTE_SUCCESS_CODE_FRONT;
            } else {
                return EXECUTE_FAILED_NETWORK_CONNECTION;
            }
        } else {
            return EXECUTE_FAILED_USER_DATA;
        }
    }
}
