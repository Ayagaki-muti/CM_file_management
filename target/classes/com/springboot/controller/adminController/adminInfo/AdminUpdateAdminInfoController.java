package com.springboot.controller.adminController.adminInfo;

import com.springboot.entity.AdminInfo;
import com.springboot.service.admin.adminInfo.AdminBrowseAdminInfoService;
import com.springboot.service.admin.adminInfo.AdminUpdateAdminInfoService;
import com.springboot.utils.myHttpResult.HttpResult;
import com.springboot.utils.myLog.Slf4j;
import com.springboot.utils.myMap.MapUntils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.springboot.config.StaticConfig.*;

@Controller
/**
 * 管理员注册控制层
 * */
@RequestMapping("admin")
public class AdminUpdateAdminInfoController {

    @Autowired
    private AdminUpdateAdminInfoService adminUpdateAdminInfoServiceImpl;
    @Autowired
    private AdminBrowseAdminInfoService adminBrowseAdminInfoServiceImpl;

    /**
     * POST接收更新信息
     */
    @PostMapping("updateAdminInfo")
    @ResponseBody
    public Object loginPost(@RequestParam("adminId") String adminId, @RequestParam("adminPassword") String adminPassword,
                            @RequestParam("adminName") String adminName, @RequestParam("adminSex") String adminSex,
                            @RequestParam("adminAddress") String adminAddress, @RequestParam("adminLevel") String adminLevel, @RequestParam("adminPhone") String adminPhone) {
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.setAdminPassword(adminPassword);
        adminInfo.setAdminId(adminId);
        adminInfo.setAdminSex(adminSex);
        adminInfo.setAdminPhone(adminPhone);
        adminInfo.setAdminName(adminName);
        adminInfo.setAdminAddress(adminAddress);
        adminInfo.setAdminLevel(adminLevel);
        Slf4j.logger.info("Controller:管理员更新" + adminInfo.toString());
        Integer result = adminUpdateAdminInfoServiceImpl.adminUpdateAdminInfo(adminInfo);
        if (result.equals(EXECUTE_SUCCESS_CODE_FRONT)) {
            Slf4j.logger.info("Controller:管理员更新成功：" + adminInfo.toString());
            return new HttpResult("更新成功", EXECUTE_SUCCESS_CODE_FRONT);
        } else if (result.equals(EXECUTE_FAILED_NETWORK_CONNECTION)) {
            Slf4j.logger.info("Controller:管理员更新失败 网络异常：" + adminInfo.toString());
            return new HttpResult("网络异常", EXECUTE_FAILED_NETWORK_CONNECTION);
        }
        return null;
    }

    /**
     * GET 获取超级管理员最新信息
     */
    @GetMapping("updateAdminInfo")
    public String loginPost(@Param("adminId") String adminId, Model model) {
        Map<String, Object> adminInfo = adminBrowseAdminInfoServiceImpl.adminBrowseAdminInfo(adminId);
        Slf4j.logger.info(adminInfo.toString());
        model.addAttribute("adminInfo", adminInfo);
        return "admin/adminInfo/updateAdminInfo";
    }

    /**
     * 修改密码
     */
    @PostMapping("updateAdminPassword")
    @ResponseBody
    public Object updateAdminPassword(@RequestParam("adminId") String adminId, @RequestParam("preAdminPassword") String preAdminPassword,
                                      @RequestParam("newAdminPassword") String newAdminPassword) {
        AdminInfo adminInfo = new AdminInfo();
        MapUntils.mapToBean(adminBrowseAdminInfoServiceImpl.adminBrowseAdminInfo(adminId), adminInfo);
        // 如果用户输入的原密码正确 进行更改操作
        if (adminInfo.getAdminPassword().equals(preAdminPassword)) {
            adminInfo.setAdminPassword(newAdminPassword);
            Slf4j.logger.info("Controller:管理员更新密码" + adminInfo.toString());
            Integer result = adminUpdateAdminInfoServiceImpl.adminUpdateAdminInfo(adminInfo);
            if (result.equals(EXECUTE_FAILED_USER_DATA)) {
                Slf4j.logger.info("Controller:管理员更新失败 网络异常：" + adminInfo.toString());
                return new HttpResult("网络异常", EXECUTE_FAILED_NETWORK_CONNECTION);
            } else {
                Slf4j.logger.info("Controller:管理员更新密码成功：" + adminInfo.toString());
                return new HttpResult("重置密码成功", EXECUTE_SUCCESS_CODE_FRONT);
            }
        } else {
            Slf4j.logger.info("Controller:管理员更新失败 原密码错误：" + adminInfo.toString());
            return new HttpResult("原密码错误", EXECUTE_FAILED_USER_DATA);
        }
    }
}
