package com.springboot.controller.publicController;

import com.springboot.utils.myLog.Slf4j;
import com.springboot.utils.mypicVerify.RandomValidateCodeUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/login")
/**
 * 生成验证码控制层
 * */
public class PicVerifyController {
    /**
     * 生成验证码
     */
    @RequestMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            //设置相应类型,告诉浏览器输出的内容为图片
            response.setContentType("image/jpeg");
            //设置响应头信息
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expire", 0);
            RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
            //输出验证码图片方法
            randomValidateCode.getRandcode(request, response);
        } catch (Exception e) {
            Slf4j.logger.info("获取验证码失败");
        }
    }

    /**
     * 校验验证码
     */
    @RequestMapping(value = "/checkVerify", method = RequestMethod.POST, headers = "Accept=application/json")
    public boolean checkVerify(@RequestParam String verifyInput, HttpSession session) {
        try {
            // 从session中获取随机数
            String inputStr = verifyInput;
            String random = (String) session.getAttribute("RANDOMVALIDATECODEKEY");
            if (random == null) {
                return false;
            }
            return random.equals(inputStr);
        } catch (Exception e) {
            Slf4j.logger.info("验证码校验失败");
            return false;
        }
    }

}
