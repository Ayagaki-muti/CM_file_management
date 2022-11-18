package com.springboot.service.user.userInfo.impl;

import com.springboot.dao.UserInfoDao;
import com.springboot.entity.UserInfo;
import com.springboot.service.user.userInfo.UserInsertUserInfoService;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.springboot.config.StaticConfig.*;


@Service
public class UserInsertUserInfoServiceImpl implements UserInsertUserInfoService {

    @Autowired
    private UserInfoDao userInfoDaoImpl;

    @Override
    public Integer userRegister(UserInfo userInfo) {
        if (userInfoDaoImpl.selectUserInfoByUserId(userInfo.getUserId()) == null) {
            Slf4j.logger.info("Service:用户注册" + userInfo.toString());
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
