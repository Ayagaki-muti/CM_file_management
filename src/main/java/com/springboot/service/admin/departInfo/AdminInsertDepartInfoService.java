package com.springboot.service.admin.departInfo;

import com.springboot.entity.DepartInfo;


public interface AdminInsertDepartInfoService {
    /**
     * 注册部门
     * 成功：200
     * 已有该用户：401
     * 网络异常：402
     */
    Integer adminInsertDepartInfo(DepartInfo departInfo);
}
