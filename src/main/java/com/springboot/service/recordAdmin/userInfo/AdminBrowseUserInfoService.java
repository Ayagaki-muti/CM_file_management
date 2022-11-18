package com.springboot.service.recordAdmin.userInfo;

import java.util.List;
import java.util.Map;


public interface AdminBrowseUserInfoService {
    /**
     * 查看指定用户的所有信息
     *
     * @return
     */
    Map<String, Object> adminBrowseUserInfoByUserId(String userId);

    /**
     * 查看所有用户的信息
     */
    List<Map<String, Object>> adminBrowseAllUserInfo();

}
