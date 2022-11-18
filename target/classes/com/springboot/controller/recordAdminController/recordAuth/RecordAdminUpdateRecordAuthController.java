package com.springboot.controller.recordAdminController.recordAuth;

import com.springboot.entity.RecordAuth;
import com.springboot.service.recordAdmin.recordAuth.RecordAdminBrowseRecordAuthService;
import com.springboot.service.recordAdmin.recordAuth.RecordAdminUpdateRecordAuthService;
import com.springboot.utils.myHttpResult.HttpResult;
import com.springboot.utils.myLog.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.springboot.config.StaticConfig.EXECUTE_FAILED_USER_DATA;
import static com.springboot.config.StaticConfig.EXECUTE_SUCCESS_CODE_FRONT;

@Controller
/**
 * 管理员更新授权
 * */
@RequestMapping("recordAdmin")
public class RecordAdminUpdateRecordAuthController {

    @Autowired
    private RecordAdminBrowseRecordAuthService adminBrowseRecordAuthServiceImpl;
    @Autowired
    private RecordAdminUpdateRecordAuthService adminUpdateRecordAuthServiceImpl;

    @GetMapping("updateRecordAuth")
    public String loginPost(@Param("recordAuthId") String recordAuthId, Model model) {
        Map<String, Object> recordAuth = adminBrowseRecordAuthServiceImpl.adminBrowseRecordAuthByRecordAuthId(recordAuthId);
        Slf4j.logger.info(recordAuth.toString());
        model.addAttribute("recordAuth", recordAuth);
        return "recordAdmin/recordAuth/updateRecordAuth";
    }

    /**
     * POST接收更新信息
     */
    @PostMapping("updateRecordAuth")
    @ResponseBody
    public Object loginPost(@RequestParam("recordAuthId") String recordAuthId, @RequestParam("recordAdminId") String recordAdminId,
                            @RequestParam("recordUserId") String recordUserId, @RequestParam("recordId") String recordId,
                            @RequestParam("recordAuthTime") String recordAuthTime, @RequestParam("recordAuthCutOffTime") String recordAuthCutOffTime,
                            @RequestParam("recordAuthTips") String recordAuthTips) {
        RecordAuth recordAuth = new RecordAuth();
        recordAuth.setRecordAuthTips(recordAuthTips);
        recordAuth.setRecordId(recordId);
        recordAuth.setRecordAuthTime(recordAuthTime);
        recordAuth.setRecordAuthId(recordAuthId);
        recordAuth.setRecordAuthCutOffTime(recordAuthCutOffTime);
        recordAuth.setRecordAdminId(recordAdminId);
        recordAuth.setRecordUserId(recordUserId);
        Slf4j.logger.info("Controller:管理员更新档案授权信息" + recordAuth.toString());
        Integer result = adminUpdateRecordAuthServiceImpl.adminUpdateRecordAuth(recordAuth);
        if (result.equals(EXECUTE_SUCCESS_CODE_FRONT)) {
            Slf4j.logger.info("Controller:更新授权信息成功：" + recordAuth.toString());
            return new HttpResult("更新成功", EXECUTE_SUCCESS_CODE_FRONT);
        } else if (result.equals(EXECUTE_FAILED_USER_DATA)) {
            Slf4j.logger.info("Controller:添加授权信息失败 网络异常：" + recordAuth.toString());
            return new HttpResult("网络异常", EXECUTE_FAILED_USER_DATA);
        }
        return null;
    }

}
