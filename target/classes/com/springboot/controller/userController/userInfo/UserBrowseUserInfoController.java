package com.springboot.controller.userController.userInfo;

import com.springboot.service.recordAdmin.userInfo.AdminBrowseUserInfoService;
import com.springboot.utils.myLog.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
/**
 * 用户登录控制层
 * */
@RequestMapping("user")
public class UserBrowseUserInfoController {

    @Autowired
    private AdminBrowseUserInfoService adminBrowseUserInfoServiceImpl;

    @GetMapping("browseUserInfo")
    public String browseUserInfo(@Param("userId") String userId, Model model) {
        Map<String, Object> userInfo = adminBrowseUserInfoServiceImpl.adminBrowseUserInfoByUserId(userId);
        Slf4j.logger.info(userInfo.toString());
        model.addAttribute("userInfo", userInfo);
        return "user/userInfo/browseUserInfo";
    }

    @GetMapping("browseAllUserInfo")
    public String browseAllRecordInfo(Model model) {
        List<Map<String, Object>> userInfo = adminBrowseUserInfoServiceImpl.adminBrowseAllUserInfo();
        model.addAttribute("userInfo", userInfo);
        return "user/userInfo/browseAllUserInfo";
    }

}
