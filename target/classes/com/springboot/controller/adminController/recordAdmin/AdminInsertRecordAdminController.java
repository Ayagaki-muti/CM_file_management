package com.springboot.controller.adminController.recordAdmin;

import com.springboot.entity.RecordAdmin;
import com.springboot.service.admin.recordAdmin.AdminInsertAdminService;
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
 * 档案管理员注册控制层
 * */
@RequestMapping("admin")
public class AdminInsertRecordAdminController {

    @Autowired
    private AdminInsertAdminService adminInsertAdminServiceImpl;

    @PostMapping("registerRecordAdmin")
    @ResponseBody
    public Object registerRecordAdmin(@RequestParam("recordAdminPassword") String recordAdminPassword,
                                      @RequestParam("recordAdminName") String recordAdminName, @RequestParam("departId") String departId,
                                      @RequestParam("recordAdminLevel") String recordAdminLevel, @RequestParam("recordAdminPhone") String recordAdminPhone) {
        RecordAdmin recordAdmin = new RecordAdmin();
        recordAdmin.setRecordAdminPhone(recordAdminPhone);
        recordAdmin.setRecordAdminPassword(recordAdminPassword);
        recordAdmin.setRecordAdminName(recordAdminName);
        recordAdmin.setRecordAdminLevel(recordAdminLevel);
        recordAdmin.setDepartId(departId);
        Slf4j.logger.info("Controller:管理员注册档案管理员" + recordAdmin.toString());
        Integer result = adminInsertAdminServiceImpl.adminInsertRecordAdmin(recordAdmin);
        if (result.equals(EXECUTE_FAILED_NETWORK_CONNECTION)) {
            Slf4j.logger.info("Controller:档案管理员注册失败 网络异常：" + recordAdmin.toString());
            return new HttpResult("网络异常", EXECUTE_FAILED_NETWORK_CONNECTION);
        } else {
            Slf4j.logger.info("Controller:档案管理员注册成功：" + recordAdmin.toString());
            return new HttpResult("注册成功", result);
        }
    }

}
