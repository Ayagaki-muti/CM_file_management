package com.springboot.service.admin.adminInfo.impl;

import com.springboot.dao.AdminInfoDao;
import com.springboot.entity.AdminInfo;
import com.springboot.service.admin.adminInfo.AdminUpdateAdminInfoService;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.springboot.config.StaticConfig.*;


@Service
public class AdminUpdateAdminInfoServiceImpl implements AdminUpdateAdminInfoService {

    @Autowired
    private AdminInfoDao adminInfoDaoImpl;

    @Override
    public Integer adminUpdateAdminInfo(AdminInfo adminInfo) {
        if (adminInfoDaoImpl.selectAdminInfoByAdminId(adminInfo.getAdminId()) != null) {
            Slf4j.logger.info("Service:管理员更新" + adminInfo.toString());
            if (adminInfoDaoImpl.insertAdminInfo(adminInfo)) {
                return EXECUTE_SUCCESS_CODE_FRONT;
            } else {
                return EXECUTE_FAILED_NETWORK_CONNECTION;
            }
        } else {
            return EXECUTE_FAILED_USER_DATA;
        }
    }
}
