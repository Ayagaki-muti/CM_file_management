package com.springboot.controller.recordAdminController.recordAdmin;

import com.springboot.entity.RecordAdmin;
import com.springboot.service.recordAdmin.recordAdmin.RecordAdminLoginAuthService;
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
 * 用户登录控制层
 * */
@RequestMapping("recordAdmin")
public class RecordAdminLoginController {

    @Autowired
    private RecordAdminLoginAuthService recordAdminLoginAuthServiceImpl;

    @PostMapping("recordAdminLogin")
    public String loginPost(@RequestParam("recordAdminId") String recordAdminId,
                            @RequestParam("recordAdminPassword") String recordAdminPassword,
                            @RequestParam("verifyInput") String verifyInput,
                            Map<String, Object> map, HttpSession httpSession, Model model) {
        if (checkVerify(verifyInput, httpSession)) {
            RecordAdmin recordAdmin = new RecordAdmin();
            recordAdmin.setRecordAdminId(recordAdminId);
            recordAdmin.setRecordAdminPassword(recordAdminPassword);
            Slf4j.logger.info("Controller:档案管理员登录" + recordAdmin.toString());
            if (recordAdminLoginAuthServiceImpl.recordAdminLoginAuth(recordAdmin)) {
                // 将UserId存入session 管理员主页面使用
                httpSession.setAttribute("recordAdminId", recordAdminId);
                return "recordAdmin/main";
            } else {
                map.put("msg", "档案管理员密码错误");
                return "recordAdmin/login";
            }
        } else {
            map.put("msg", "验证码错误");
            return "recordAdmin/login";
        }
    }

}
