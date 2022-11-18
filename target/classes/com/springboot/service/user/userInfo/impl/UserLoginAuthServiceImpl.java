package com.springboot.service.user.userInfo.impl;

import com.springboot.dao.UserInfoDao;
import com.springboot.entity.UserInfo;
import com.springboot.service.user.userInfo.UserLoginAuthService;
import com.springboot.utils.myMap.MapUntils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserLoginAuthServiceImpl implements UserLoginAuthService {

    @Autowired
    private UserInfoDao userInfoDaoImpl;

    @Override
    public boolean userLoginAuth(UserInfo userInfo) {
        try {
            UserInfo userInfoBean = new UserInfo();
            MapUntils.mapToBean(userInfoDaoImpl.selectUserInfoByUserId(userInfo.getUserId()), userInfoBean);
            return userInfo.getUserPassword().equals(userInfoBean.getUserPassword());
        } catch (NullPointerException ex) {
            return false;
        }
    }
}
