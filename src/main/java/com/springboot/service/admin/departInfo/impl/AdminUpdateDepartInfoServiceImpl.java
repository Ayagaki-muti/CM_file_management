package com.springboot.service.admin.departInfo.impl;

import com.springboot.dao.DepartInfoDao;
import com.springboot.entity.DepartInfo;
import com.springboot.service.admin.departInfo.AdminUpdateDepartInfoService;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.springboot.config.StaticConfig.*;


@Service
public class AdminUpdateDepartInfoServiceImpl implements AdminUpdateDepartInfoService {

    @Autowired
    private DepartInfoDao departInfoDaoImpl;

    @Override
    public Integer adminUpdateDepartInfo(DepartInfo departInfo) {
        if (departInfoDaoImpl.selectDepartInfoByDepartId(departInfo.getDepartId()) != null) {
            Slf4j.logger.info("Service:更新部门信息" + departInfo.toString());
            if (departInfoDaoImpl.insertDepartInfo(departInfo)) {
                return EXECUTE_SUCCESS_CODE_FRONT;
            } else {
                return EXECUTE_FAILED_NETWORK_CONNECTION;
            }
        } else {
            return EXECUTE_FAILED_USER_DATA;
        }
    }
}
