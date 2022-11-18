package com.springboot.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//url与前端页面的映射，无业务逻辑的跳转
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 为了跟控制层请求区分，每个请求地址后面添加View
     */
    //MyMvcConfig处理"/main.html"请求，转到dashboard.html页面
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        /**
         * 超级管理员URL
         * */
        // 超级管理员登录
        registry.addViewController("/admin/adminLoginView").setViewName("admin/login");
        // 重置超级管理员密码
        registry.addViewController("/admin/updateAdminPasswordView").setViewName("admin/adminInfo/updateAdminPassword");
        // 上传链码
        registry.addViewController("/admin/uploadChaincodeView").setViewName("admin/chaincode/uploadChaincode");
        // 添加档案
        registry.addViewController("/admin/insertRecordInfoView").setViewName("admin/recordInfo/insertRecordInfo");
        // 管理员添加授权
        registry.addViewController("/admin/insertRecordAuthView").setViewName("admin/recordAuth/insertRecordAuth");
        // 管理员管理chaincode
        registry.addViewController("/admin/chaincodeView").setViewName("admin/chaincode/chaincode");
        // 管理员注册超级管理员
        registry.addViewController("/admin/insertAdminInfoView").setViewName("admin/adminInfo/insertAdminInfo");
        // 管理员注册档案管理员
        registry.addViewController("/admin/insertRecordAdminView").setViewName("admin/recordAdmin/insertRecordAdmin");
        // 管理员注册用户
        registry.addViewController("/admin/insertUserInfoView").setViewName("admin/userInfo/insertUserInfo");
        // 管理员添加部门信息
        registry.addViewController("/admin/insertDepartInfoView").setViewName("admin/departInfo/insertDepartInfo");

        /**
         * 档案管理员界面
         * */
        // 档案管理员登录
        registry.addViewController("/recordAdmin/recordAdminLoginView").setViewName("recordAdmin/login");
        // 添加档案
        registry.addViewController("/recordAdmin/insertRecordInfoView").setViewName("recordAdmin/recordInfo/insertRecordInfo");
        // 管理员添加授权
        registry.addViewController("/recordAdmin/insertRecordAuthView").setViewName("recordAdmin/recordAuth/insertRecordAuth");

        /**
         * 普通用户（档案所属人）界面
         * */
        // 登录
        registry.addViewController("/user/userLoginView").setViewName("user/login");
        // 注册
        registry.addViewController("/user/insertUserInfoView").setViewName("user/userInfo/insertUserInfo");

    }

}
