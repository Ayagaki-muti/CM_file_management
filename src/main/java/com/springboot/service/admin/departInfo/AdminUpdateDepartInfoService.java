package com.springboot.service.admin.departInfo;

import com.springboot.entity.DepartInfo;


public interface AdminUpdateDepartInfoService {
    /**
     * 部门更新
     * 成功：200
     * 找不到用户：401
     * 网络异常：402
     */
    Integer adminUpdateDepartInfo(DepartInfo departInfo);
}
