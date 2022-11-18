package com.springboot.controller.adminController.adminInfo;

import com.springboot.service.admin.adminInfo.AdminBrowseAdminInfoService;
import com.springboot.utils.myLog.Slf4j;
import com.springboot.utils.myMap.MapUntils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
/**
 * List<Map<String,Object>>
 * 用户登录控制层
 * */
@RequestMapping("admin")
public class AdminBrowseAdminInfoController {

    @Autowired
    private AdminBrowseAdminInfoService adminBrowseAdminInfoServiceImpl;

    @GetMapping("browseAdminInfo")
    public String loginPost(@Param("adminId") String adminId, Model model) {
        Map<String, Object> adminInfo = adminBrowseAdminInfoServiceImpl.adminBrowseAdminInfo(adminId);
        Slf4j.logger.info(adminInfo.toString());
        model.addAttribute("adminInfo", adminInfo);//向前端传数据
        return "admin/adminInfo/browseAdminInfo";
    }

    @GetMapping("browseAllAdminInfo")
    public String browseAllAdminInfo(Model model) {
        List<Map<String, Object>> adminInfo = adminBrowseAdminInfoServiceImpl.adminBrowseAllAdminInfo();
        Slf4j.logger.info("AdminBrowseDepartInfoController :：" + MapUntils.printListMap(adminInfo));
        model.addAttribute("adminInfo", adminInfo);
        return "admin/adminInfo/browseAllAdminInfo";
    }

}
