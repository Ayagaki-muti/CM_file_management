package com.springboot.controller.recordAdminController.recordAuth;

import com.springboot.service.recordAdmin.recordAuth.RecordAdminBrowseRecordAuthService;
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
 * 管理员浏览授权信息
 * */
@RequestMapping("recordAdmin")
public class RecordAdminBrowseRecordAuthController {

    @Autowired
    private RecordAdminBrowseRecordAuthService adminBrowseRecordAuthServiceImpl;

    /**
     * 根据档案记录id查看当前最新记录
     */
    @GetMapping("browseRecordAuth")
    public String browseRecordInfo(@Param("recordAuthId") String recordAuthId, Model model) {
        Map<String, Object> recordAuth = adminBrowseRecordAuthServiceImpl.adminBrowseRecordAuthByRecordAuthId(recordAuthId);
        model.addAttribute("recordAuth", recordAuth);
        return "recordAdmin/recordAuth/browseRecordAuth";
    }

    /**
     * 查看所有档案的最新记录
     */
    @GetMapping("browseAllRecordAuth")
    public String browseAllRecordAuth(Model model) {
        List<Map<String, Object>> recordAuth = adminBrowseRecordAuthServiceImpl.adminBrowseAllRecordAuth();
        model.addAttribute("recordAuth", recordAuth);
        return "recordAdmin/recordAuth/browseAllRecordAuth";
    }


    /**
     * 根据档案记录id查看该授权ID的所有记录
     */
    @GetMapping("browseAllRecordAuthByRecordAuthId")
    public String browseAllRecordAuthByRecordAuthId(@Param("recordAuthId") String recordAuthId, Model model) {
        List<Map<String, Object>> recordAuth = adminBrowseRecordAuthServiceImpl.adminBrowseAllRecordAuthByRecordAuthId(recordAuthId);
        Slf4j.logger.info("Controller：" + recordAuth.toString());
        model.addAttribute("recordAuth", recordAuth);
        return "recordAdmin/recordAuth/browseAllRecordAuthByRecordAuthId";
    }
}
