package com.springboot.controller.recordAdminController.recordInfo;

import com.springboot.service.recordAdmin.recordInfo.RecordAdminBrowseRecordInfoService;
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
@RequestMapping("recordAdmin")
public class RecordAdminBrowseRecordInfoController {

    @Autowired
    private RecordAdminBrowseRecordInfoService adminBrowseRecordInfoServiceImpl;

    @GetMapping("browseRecordInfo")
    public String browseRecordInfo(@Param("recordId") String recordId, Model model) {
        Map<String, Object> recordInfo = adminBrowseRecordInfoServiceImpl.adminBrowseRecordInfoByRecordId(recordId);
        Slf4j.logger.info(recordInfo.toString());
        model.addAttribute("recordInfo", recordInfo);
        return "recordAdmin/recordInfo/browseRecordInfo";
    }

    @GetMapping("browseAllRecordInfo")
    public String browseAllRecordInfo(Model model) {
        List<Map<String, Object>> recordInfo = adminBrowseRecordInfoServiceImpl.adminBrowseAllRecordInfo();
        Slf4j.logger.info("AdminBrowseRecordInfoController :：" + MapUntils.printListMap(recordInfo));
        model.addAttribute("recordInfo", recordInfo);
        return "recordAdmin/recordInfo/browseAllRecordInfo";
    }

    /**
     * 根据档案ID查看该档案的所有授权记录
     */
    @GetMapping("browseAllRecordAuthByRecordId")
    public String browseAllRecordInfoAuthByRecordInfoId(@Param("recordId") String recordId, Model model) {
        List<Map<String, Object>> recordInfo = adminBrowseRecordInfoServiceImpl.adminBrowseAllRecordInfoByRecordId(recordId);
        model.addAttribute("recordInfo", recordInfo);
        return "recordAdmin/recordInfo/browseAllRecordInfoByRecordId";
    }
}
