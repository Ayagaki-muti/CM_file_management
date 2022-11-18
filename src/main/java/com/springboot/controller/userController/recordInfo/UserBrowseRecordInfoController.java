package com.springboot.controller.userController.recordInfo;

import com.springboot.service.user.recordInfo.UserBrowseRecordInfoService;
import com.springboot.utils.myLog.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
/**
 * 用户浏览档案信息
 * */
@RequestMapping("user")
public class UserBrowseRecordInfoController {

    @Autowired
    private UserBrowseRecordInfoService adminBrowseRecordInfoServiceImpl;

    @GetMapping("browseRecordInfo")
    public String browseRecordInfo(@Param("recordId") String recordId, Model model) {
        Map<String, Object> recordInfo = adminBrowseRecordInfoServiceImpl.userBrowseRecordInfoByRecordId(recordId);
        Slf4j.logger.info(recordInfo.toString());
        model.addAttribute("recordInfo", recordInfo);
        return "user/recordInfo/browseRecordInfo";
    }


}
