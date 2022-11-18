package com.springboot.service.admin.departInfo;

import java.util.List;
import java.util.Map;


public interface AdminBrowseDepartInfoService {
    /**
     * 查看部门的信息
     *
     * @return
     */
    Map<String, Object> adminBrowseDepartInfo(String departId);

    /**
     * 查看所有部门的信息
     */
    List<Map<String, Object>> adminBrowseAllDepartInfo();
}
