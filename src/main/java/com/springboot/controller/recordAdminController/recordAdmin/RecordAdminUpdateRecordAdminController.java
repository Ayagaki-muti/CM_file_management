package com.springboot.controller.recordAdminController.recordAdmin;

import com.springboot.entity.RecordAdmin;
import com.springboot.service.recordAdmin.recordAdmin.RecordAdminBrowseRecordAdminService;
import com.springboot.service.recordAdmin.recordAdmin.RecordAdminUpdateRecordAdminService;
import com.springboot.utils.myHttpResult.HttpResult;
import com.springboot.utils.myLog.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.springboot.config.StaticConfig.EXECUTE_FAILED_NETWORK_CONNECTION;
import static com.springboot.config.StaticConfig.EXECUTE_SUCCESS_CODE_FRONT;

@Controller
/**
 * 管理员注册控制层
 * */
@RequestMapping("recordAdmin")
public class RecordAdminUpdateRecordAdminController {

    @Autowired
    private RecordAdminUpdateRecordAdminService adminUpdateAdminInfoServiceImpl;
    @Autowired
    private RecordAdminBrowseRecordAdminService recordAdminBrowseRecordAdminServiceImpl;

    /**
     * POST接收更新信息
     */
    @PostMapping("updateRecordAdmin")
    @ResponseBody
    public Object updateRecordAdmin(@RequestParam("recordAdminId") String recordAdminId, @RequestParam("recordAdminPassword") String recordAdminPassword,
                                    @RequestParam("recordAdminName") String recordAdminName, @RequestParam("departId") String departId,
                                    @RequestParam("recordAdminLevel") String recordAdminLevel, @RequestParam("recordAdminPhone") String recordAdminPhone) {
        RecordAdmin recordAdmin = new RecordAdmin();
        recordAdmin.setDepartId(departId);
        recordAdmin.setRecordAdminId(recordAdminId);
        recordAdmin.setRecordAdminLevel(recordAdminLevel);
        recordAdmin.setRecordAdminName(recordAdminName);
        recordAdmin.setRecordAdminPassword(recordAdminPassword);
        recordAdmin.setRecordAdminPhone(recordAdminPhone);
        Slf4j.logger.info("Controller:管理员更新档案管理员" + recordAdmin.toString());
        Integer result = adminUpdateAdminInfoServiceImpl.RecordAdminUpdateRecordAdmin(recordAdmin);
        if (result.equals(EXECUTE_SUCCESS_CODE_FRONT)) {
            Slf4j.logger.info("Controller:管理员更新档案管理员成功：" + recordAdmin.toString());
            return new HttpResult("更新成功", EXECUTE_SUCCESS_CODE_FRONT);
        } else if (result.equals(EXECUTE_FAILED_NETWORK_CONNECTION)) {
            Slf4j.logger.info("Controller:管理员更新档案管理员失败 网络异常：" + recordAdmin.toString());
            return new HttpResult("网络异常", EXECUTE_FAILED_NETWORK_CONNECTION);
        }
        return null;
    }

    /**
     * GET 获取超级管理员最新信息
     */
    @GetMapping("updateRecordAdmin")
    public String loginPost(@Param("recordAdminId") String recordAdminId, Model model) {
        Map<String, Object> recordAdmin = recordAdminBrowseRecordAdminServiceImpl.recordAdminBrowseRecordAdmin(recordAdminId);
        Slf4j.logger.info(recordAdmin.toString());
        model.addAttribute("recordAdmin", recordAdmin);
        return "recordAdmin/recordAdmin/updateRecordAdmin";
    }

}
