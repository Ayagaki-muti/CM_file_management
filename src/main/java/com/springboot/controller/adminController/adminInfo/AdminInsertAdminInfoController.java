package com.springboot.controller.adminController.adminInfo;

import com.springboot.entity.AdminInfo;
import com.springboot.service.admin.adminInfo.AdminInsertAdminInfoService;
import com.springboot.utils.myHttpResult.HttpResult;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.springboot.config.StaticConfig.EXECUTE_FAILED_USER_DATA;

@Controller
/**
 * 管理员注册控制层（超级管理员注册）
 * */
@RequestMapping("admin")
public class AdminInsertAdminInfoController {

    @Autowired
    private AdminInsertAdminInfoService adminInsertAdminInfoServiceImpl;

    @PostMapping("insertAdminInfo")
    @ResponseBody
    //接收数据
    public Object loginPost(@RequestParam("adminPassword") String adminPassword,
                            @RequestParam("adminName") String adminName, @RequestParam("adminSex") String adminSex,
                            @RequestParam("adminAddress") String adminAddress, @RequestParam("adminLevel") String adminLevel, @RequestParam("adminPhone") String adminPhone) {
        AdminInfo adminInfo = new AdminInfo();//封装
        adminInfo.setAdminPassword(adminPassword);//设置字段
        adminInfo.setAdminSex(adminSex);
        adminInfo.setAdminPhone(adminPhone);
        adminInfo.setAdminName(adminName);
        adminInfo.setAdminAddress(adminAddress);
        adminInfo.setAdminLevel(adminLevel);
        Slf4j.logger.info("Controller:管理员注册" + adminInfo.toString());//输出日志
        Integer result = adminInsertAdminInfoServiceImpl.adminInsertAdminInfo(adminInfo);//调用server层
        if (result.equals(EXECUTE_FAILED_USER_DATA)) {
            Slf4j.logger.info("Controller:管理员注册失败 网络异常：" + adminInfo.toString());
            return new HttpResult("网络异常", EXECUTE_FAILED_USER_DATA);
        } else {
            Slf4j.logger.info("Controller:管理员注册成功：" + adminInfo.toString());
            return new HttpResult("注册成功", result);
        }
    }

}
