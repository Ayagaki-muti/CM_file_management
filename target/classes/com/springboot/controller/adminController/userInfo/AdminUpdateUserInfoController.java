package com.springboot.controller.adminController.userInfo;

import com.springboot.entity.UserInfo;
import com.springboot.service.recordAdmin.userInfo.AdminBrowseUserInfoService;
import com.springboot.service.recordAdmin.userInfo.AdminUpdateUsrInfoService;
import com.springboot.utils.myHttpResult.HttpResult;
import com.springboot.utils.myLog.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.springboot.config.StaticConfig.EXECUTE_FAILED_NETWORK_CONNECTION;
import static com.springboot.config.StaticConfig.EXECUTE_SUCCESS_CODE_FRONT;

@Controller
/**
 * 管理员注册控制层
 * */
@RequestMapping("admin")
public class AdminUpdateUserInfoController {

    @Autowired
    private AdminUpdateUsrInfoService adminUpdateUsrInfoServiceImpl;
    @Autowired
    private AdminBrowseUserInfoService adminBrowseUserInfoServiceImpl;

    /**
     * GET 获取超级管理员最新信息
     */
    @GetMapping("updateUserInfo")
    public String loginPost(@Param("userId") String userId, Model model) {
        Map<String, Object> userInfo = adminBrowseUserInfoServiceImpl.adminBrowseUserInfoByUserId(userId);
        Slf4j.logger.info(userInfo.toString());
        model.addAttribute("userInfo", userInfo);
        return "admin/userInfo/updateUserInfo";
    }

    /**
     * POST接收更新信息
     */
    @PostMapping("updateUserInfo")
    @ResponseBody
    public Object updateUserInfo(@RequestParam("userId") String userId, @RequestParam("userPassword") String userPassword,
                                 @RequestParam("userName") String userName, @RequestParam("userSex") String userSex,
                                 @RequestParam("userAddress") String userAddress, @RequestParam("userPhone") String userPhone) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserAddress(userAddress);
        userInfo.setUserId(userId);
        userInfo.setUserName(userName);
        userInfo.setUserPassword(userPassword);
        userInfo.setUserPhone(userPhone);
        userInfo.setUserSex(userSex);
        Slf4j.logger.info("Controller:管理员更新用户" + userInfo.toString());
        Integer result = adminUpdateUsrInfoServiceImpl.adminUpdateUserInfo(userInfo);
        if (result.equals(EXECUTE_SUCCESS_CODE_FRONT)) {
            Slf4j.logger.info("Controller:管理员更新用户成功：" + userInfo.toString());
            return new HttpResult("更新成功", EXECUTE_SUCCESS_CODE_FRONT);
        } else if (result.equals(EXECUTE_FAILED_NETWORK_CONNECTION)) {
            Slf4j.logger.info("Controller:管理员更新用户失败 网络异常：" + userInfo.toString());
            return new HttpResult("网络异常", EXECUTE_FAILED_NETWORK_CONNECTION);
        }
        return null;
    }

}
