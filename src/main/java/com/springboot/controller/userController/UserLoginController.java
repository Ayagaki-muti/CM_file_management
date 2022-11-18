package com.springboot.controller.userController;

import com.springboot.entity.UserInfo;
import com.springboot.service.user.userInfo.UserLoginAuthService;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("user")
public class UserLoginController {

    @Autowired
    private UserLoginAuthService userLoginAuthServiceImpl;

    @PostMapping("userLogin")
    public String loginPost(@RequestParam("userId") String userId,
                            @RequestParam("userPassword") String userPassword,
                            @RequestParam("verifyInput") String verifyInput,
                            Map<String, Object> map, HttpSession httpSession) {
        if (checkVerify(verifyInput, httpSession)) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(userId);
            userInfo.setUserPassword(userPassword);
            Slf4j.logger.info("Controller:用户登录" + userInfo.toString());
            if (userLoginAuthServiceImpl.userLoginAuth(userInfo)) {
                // 将UserId存入session 用户主页面使用
                httpSession.setAttribute("userId", userId);
                return "user/main";
            } else {
                map.put("msg", "用户名密码错误");
                return "user/login";
            }
        } else {
            map.put("msg", "验证码错误");
            return "user/login";
        }
    }


}
