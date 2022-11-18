package com.springboot.controller.adminController.recordInfo;

import com.springboot.entity.RecordInfo;
import com.springboot.service.admin.recordInfo.AdminBrowseRecordInfoService;
import com.springboot.service.admin.recordInfo.AdminUpdateRecordInfoService;
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
 * 更新档案信息
 *
 * */
@RequestMapping("admin")
public class AdminUpdateRecordInfoController {

    @Autowired
    private AdminBrowseRecordInfoService adminBrowseRecordInfoServiceImpl;
    @Autowired
    private AdminUpdateRecordInfoService adminUpdateRecordInfoServiceImpl;

    @GetMapping("updateRecordInfo")
    public String loginPost(@Param("recordId") String recordId, Model model) {
        Map<String, Object> recordInfo = adminBrowseRecordInfoServiceImpl.adminBrowseRecordInfoByRecordId(recordId);
        Slf4j.logger.info(recordInfo.toString());
        model.addAttribute("recordInfo", recordInfo);
        return "admin/recordInfo/updateRecordInfo";
    }

    /**
     * POST接收更新信息
     */
    @PostMapping("updateRecordInfo")
    @ResponseBody
    public Object loginPost(@RequestParam("recordId") String recordId, @RequestParam("recordAdminId") String recordAdminId,
                            @RequestParam("recordName") String recordName, @RequestParam("recordVersion") String recordVersion,
                            @RequestParam("recordTime") String recordTime, @RequestParam("fileHash") String fileHash,
                            @RequestParam("recordDescribe") String recordDescribe) {
        RecordInfo recordInfo = new RecordInfo();
        recordInfo.setRecordVersion(recordVersion);
        recordInfo.setRecordTime(recordTime);
        recordInfo.setRecordName(recordName);
        recordInfo.setRecordId(recordId);
        recordInfo.setRecordDescribe(recordDescribe);
        recordInfo.setRecordAdminId(recordAdminId);
        recordInfo.setFileHash(fileHash);
        Slf4j.logger.info("Controller:管理员添加档案信息" + recordInfo.toString());
        Integer result = adminUpdateRecordInfoServiceImpl.adminUpdateRecordInfoByRecordId(recordInfo);
        if (result.equals(EXECUTE_SUCCESS_CODE_FRONT)) {
            Slf4j.logger.info("Controller:添加成功：" + recordInfo.toString());
            return new HttpResult("更新成功", EXECUTE_SUCCESS_CODE_FRONT);
        } else if (result.equals(EXECUTE_FAILED_NETWORK_CONNECTION)) {
            Slf4j.logger.info("Controller:添加失败 网络异常：" + recordInfo.toString());
            return new HttpResult("网络异常", EXECUTE_FAILED_NETWORK_CONNECTION);
        }
        return null;
    }

}
