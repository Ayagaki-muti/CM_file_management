package com.springboot.controller.adminController.userInfo;

import com.springboot.entity.UserInfo;
import com.springboot.service.recordAdmin.userInfo.AdminInsertUserInfoService;
import com.springboot.utils.myHttpResult.HttpResult;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.springboot.config.StaticConfig.EXECUTE_FAILED_NETWORK_CONNECTION;

@Controller
/**
 * 用户注册控制层
 * */
@RequestMapping("admin")
public class AdminInsertUserInfoController {

    @Autowired
    private AdminInsertUserInfoService adminInsertUserInfoServiceImpl;

    @PostMapping("insertUserInfo")
    @ResponseBody
    public Object insertUserInfo(@RequestParam("userPassword") String userPassword,
                                 @RequestParam("userName") String userName, @RequestParam("userSex") String userSex,
                                 @RequestParam("userAddress") String userAddress, @RequestParam("userPhone") String userPhone) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserSex(userSex);
        userInfo.setUserPhone(userPhone);
        userInfo.setUserPassword(userPassword);
        userInfo.setUserName(userName);
        userInfo.setUserAddress(userAddress);
        Slf4j.logger.info("Controller:管理员注册用户" + userInfo.toString());
        Integer result = adminInsertUserInfoServiceImpl.adminRegisterUserInfo(userInfo);
        if (result.equals(EXECUTE_FAILED_NETWORK_CONNECTION)) {
            Slf4j.logger.info("Controller:用户注册失败 网络异常：" + userInfo.toString());
            return new HttpResult("网络异常", EXECUTE_FAILED_NETWORK_CONNECTION);
        } else {
            Slf4j.logger.info("Controller:用户注册成功：" + userInfo.toString());
            return new HttpResult("注册成功", result);
        }
    }

}
