package com.springboot.controller.adminController.departInfo;

import com.springboot.service.admin.departInfo.AdminBrowseDepartInfoService;
import com.springboot.utils.myLog.Slf4j;
import com.springboot.utils.myMap.MapUntils;
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
 * 用户浏览档案信息
 * */
@RequestMapping("admin")
public class AdminBrowseDepartInfoController {

    @Autowired
    private AdminBrowseDepartInfoService adminBrowseDepartInfoServiceImpl;

    @GetMapping("browseDepartInfo")
    public String browseDepartInfo(@Param("departId") String departId, Model model) {
        Map<String, Object> departInfo = adminBrowseDepartInfoServiceImpl.adminBrowseDepartInfo(departId);
        Slf4j.logger.info(departInfo.toString());
        model.addAttribute("departInfo", departInfo);
        return "admin/departInfo/browseDepartInfo";
    }

    @GetMapping("browseAllDepartInfo")
    public String browseAllDepartInfo(Model model) {
        List<Map<String, Object>> departInfo = adminBrowseDepartInfoServiceImpl.adminBrowseAllDepartInfo();
        Slf4j.logger.info("AdminBrowseDepartInfoController :：" + MapUntils.printListMap(departInfo));
        model.addAttribute("departInfo", departInfo);
        return "admin/departInfo/browseAllDepartInfo";
    }

}
