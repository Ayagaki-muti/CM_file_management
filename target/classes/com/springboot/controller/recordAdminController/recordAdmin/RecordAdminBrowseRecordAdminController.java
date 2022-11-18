package com.springboot.controller.recordAdminController.recordAdmin;

import com.springboot.service.admin.recordAdmin.AdminBrowseRecordAdminService;
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
@RequestMapping("recordAdmin")
public class RecordAdminBrowseRecordAdminController {

    @Autowired
    private AdminBrowseRecordAdminService adminBrowseRecordAdminServiceImpl;

    @GetMapping("browseRecordAdmin")
    public String browseRecordAdmin(@Param("recordAdminId") String recordAdminId, Model model) {
        Map<String, Object> recordAdmin = adminBrowseRecordAdminServiceImpl.adminBrowseRecordAdmin(recordAdminId);
        Slf4j.logger.info(recordAdmin.toString());
        model.addAttribute("recordAdmin", recordAdmin);
        return "recordAdmin/recordAdmin/browseRecordAdmin";
    }

    @GetMapping("browseAllRecordAdmin")
    public String browseAllRecordAdmin(Model model) {
        List<Map<String, Object>> recordAdmin = adminBrowseRecordAdminServiceImpl.adminBrowseAllRecordAdmin();
        Slf4j.logger.info(recordAdmin.toString());
        model.addAttribute("recordAdmin", recordAdmin);
        return "admin/recordAdmin/browseAllRecordAdmin";
    }

}
