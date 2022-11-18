package com.springboot.controller.userController.recordAuth;

import com.springboot.service.user.recordAuth.UserBrowseRecordAuthService;
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
 * 管理员浏览授权信息
 * */
@RequestMapping("user")
public class UserBrowseRecordAuthController {

    @Autowired
    private UserBrowseRecordAuthService adminBrowseRecordAuthServiceImpl;

    /**
     * 根据档案记录id查看当前最新记录
     */
    @GetMapping("browseRecordAuth")
    public String browseRecordInfo(@Param("recordAuthId") String recordAuthId, Model model) {
        Map<String, Object> recordAuth = adminBrowseRecordAuthServiceImpl.adminBrowseRecordAuthByRecordAuthId(recordAuthId);
        model.addAttribute("recordAuth", recordAuth);
        return "user/recordAuth/browseRecordAuth";
    }

    /**
     * 查看所有档案的最新记录
     */
    @GetMapping("browseAllRecordAuthByUserId")
    public String browseAllRecordAuth(Model model, @Param("userId") String userId) {
        List<Map<String, Object>> recordAuth = adminBrowseRecordAuthServiceImpl.userBrowseAllRecordAuthByUserId(userId);
        model.addAttribute("recordAuth", recordAuth);
        return "user/recordAuth/browseAllRecordAuth";
    }

}
