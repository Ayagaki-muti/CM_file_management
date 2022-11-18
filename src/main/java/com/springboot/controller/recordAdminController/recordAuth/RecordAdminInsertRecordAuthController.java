package com.springboot.controller.recordAdminController.recordAuth;

import com.springboot.entity.RecordAuth;
import com.springboot.service.recordAdmin.recordAuth.RecordAdminInsertRecordAuthService;
import com.springboot.utils.myHttpResult.HttpResult;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.springboot.config.StaticConfig.EXECUTE_FAILED_NETWORK_CONNECTION;
import static com.springboot.config.StaticConfig.EXECUTE_FAILED_USER_DATA;

@Controller
/**
 * 管理员添加授权
 * */
@RequestMapping("recordAdmin")
public class RecordAdminInsertRecordAuthController {

    @Autowired
    private RecordAdminInsertRecordAuthService adminInsertRecordAuthServiceImpl;

    /**
     * POST接收更新信息
     */
    @PostMapping("insertRecordAuth")
    @ResponseBody
    public Object insertRecordAuth(@RequestParam("recordUserId") String recordUserId, @RequestParam("recordId") String recordId, @RequestParam("recordAdminId") String recordAdminId,
                                   @RequestParam("recordAuthTime") String recordAuthTime, @RequestParam("recordAuthCutOffTime") String recordAuthCutOffTime,
                                   @RequestParam("recordAuthTips") String recordAuthTips) {
        RecordAuth recordAuth = new RecordAuth();
        recordAuth.setRecordAuthTips(recordAuthTips);
        recordAuth.setRecordId(recordId);
        recordAuth.setRecordAuthTime(recordAuthTime);
        recordAuth.setRecordAdminId(recordAdminId);
        recordAuth.setRecordAuthCutOffTime(recordAuthCutOffTime);
        recordAuth.setRecordUserId(recordUserId);
        Slf4j.logger.info("Controller:档案管理员添加档案信息" + recordAuth.toString());
        Integer result = adminInsertRecordAuthServiceImpl.adminInsertRecordAuth(recordAuth);
        if (result.equals(EXECUTE_FAILED_NETWORK_CONNECTION)) {
            Slf4j.logger.info("Controller:添加授权信息失败 网络异常：" + recordAuth.toString());
            return new HttpResult("网络异常", EXECUTE_FAILED_NETWORK_CONNECTION);
        } else if (result.equals(EXECUTE_FAILED_USER_DATA)) {
            Slf4j.logger.info("Controller:添加授权信息成功：" + recordAuth.toString());
            return new HttpResult("已经授权过该用户此档案", result);
        } else {
            Slf4j.logger.info("Controller:添加授权信息成功：" + recordAuth.toString());
            return new HttpResult("添加成功", result);
        }
    }

}
