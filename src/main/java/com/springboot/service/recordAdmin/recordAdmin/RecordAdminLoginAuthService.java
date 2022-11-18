package com.springboot.service.recordAdmin.recordAdmin;

import com.springboot.entity.RecordAdmin;


public interface RecordAdminLoginAuthService {

    /**
     * 登录验证
     *
     * @param recordAdmin:RecordAdmin 内含ID和密码用于验证是否可以登陆
     * @return boolean
     */
    boolean recordAdminLoginAuth(RecordAdmin recordAdmin);
}
