package com.springboot.service.admin.departInfo.impl;

import com.springboot.dao.DepartInfoDao;
import com.springboot.dao.KeyValueDao;
import com.springboot.entity.DepartInfo;
import com.springboot.service.admin.departInfo.AdminInsertDepartInfoService;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.springboot.config.StaticConfig.*;


@Service
public class AdminInsertDepartInfoServiceImpl implements AdminInsertDepartInfoService {

    @Autowired
    private DepartInfoDao departInfoDaoImpl;
    @Autowired
    private KeyValueDao keyValueDaoImpl;

    @Override
    public Integer adminInsertDepartInfo(DepartInfo departInfo) {
        Integer key = null;
        try {
            key = keyValueDaoImpl.getNextChainCodeId("departInfo");
            departInfo.setDepartId(String.valueOf(key));
        } catch (NullPointerException e) {
            Slf4j.logger.info("当前记录为第一个");
            key = Integer.valueOf(INITIAL_VALUE_DEPARTID);
            departInfo.setDepartId(INITIAL_VALUE_DEPARTID);
        } finally {
            if (departInfoDaoImpl.selectDepartInfoByDepartId(departInfo.getDepartId()) == null) {
                Slf4j.logger.info("Service:注册部门" + departInfo.toString());
                if (departInfoDaoImpl.insertDepartInfo(departInfo)) {
                    return key;
                } else {
                    return EXECUTE_FAILED_NETWORK_CONNECTION;
                }
            } else {
                Slf4j.logger.info("Service:注册部门已存在" + departInfo.toString());
                return EXECUTE_FAILED_USER_DATA;
            }
        }

    }
}
