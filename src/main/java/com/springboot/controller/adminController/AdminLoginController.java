package com.springboot.controller.adminController;

import com.springboot.entity.AdminInfo;
import com.springboot.service.admin.adminInfo.AdminLoginAuthService;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;

import static com.springboot.utils.mypicVerify.RandomValidateCodeUtil.checkVerify;

@Controller
/**
 * 超级管理员登录控制层
 * */
@RequestMapping("admin")
public class AdminLoginController {

    @Autowired
    private AdminLoginAuthService adminLoginAuthServiceImpl;
    //LoginController处理./adminLogin发起得post请求，重定向到main.html
    @PostMapping("adminLogin")
    public String loginPost(@RequestParam("adminId") String adminId,
                            @RequestParam("adminPassword") String adminPassword,
                            @RequestParam("verifyInput") String verifyInput,
                            Map<String, Object> map, HttpSession httpSession, Model model) {
        if (checkVerify(verifyInput, httpSession)) {
            AdminInfo adminInfo = new AdminInfo();
            adminInfo.setAdminId(adminId);
            adminInfo.setAdminPassword(adminPassword);
            Slf4j.logger.info("Controller:管理员登录" + adminInfo.toString());
            if (adminLoginAuthServiceImpl.adminLoginAuth(adminInfo)) {
                // 将UserId存入session 管理员主页面使用
                //登陆成功，防止表单重复提交，可以重定向到主页
                httpSession.setAttribute("adminId", adminId);
                return "admin/main";
            }
            //登陆失败
            else {
                map.put("msg", "管理员密码错误");
                return "admin/login";
            }
        } else {
            map.put("msg", "验证码错误");
            return "admin/login";
        }
    }

}
