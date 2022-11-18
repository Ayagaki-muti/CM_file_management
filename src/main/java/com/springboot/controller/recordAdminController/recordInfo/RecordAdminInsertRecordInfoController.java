package com.springboot.controller.recordAdminController.recordInfo;

import com.springboot.entity.RecordInfo;
import com.springboot.service.recordAdmin.recordInfo.RecordAdminInsertRecordInfoService;
import com.springboot.utils.myFile.FileUtils;
import com.springboot.utils.myHttpResult.HttpResult;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import static com.springboot.config.StaticConfig.EXECUTE_FAILED_NETWORK_CONNECTION;

@Controller
/**
 * 管理员注册控制层
 * */
@RequestMapping("recordAdmin")
public class RecordAdminInsertRecordInfoController {

    @Autowired
    private RecordAdminInsertRecordInfoService adminInsertRecordInfoServiceImpl;

    /**
     * POST接收更新信息
     */
    @PostMapping("insertRecordInfo")
    @ResponseBody
    public Object insertRecordInfo(@RequestParam("recordAdminId") String recordAdminId, @RequestParam("recordName") String recordName, @RequestParam("recordVersion") String recordVersion,
                                   @RequestParam("recordTime") String recordTime, @RequestParam("file") MultipartFile file,
                                   @RequestParam("recordDescribe") String recordDescribe) {
        RecordInfo recordInfo = new RecordInfo();
        recordInfo.setRecordVersion(recordVersion);
        recordInfo.setRecordAdminId(recordAdminId);
        recordInfo.setRecordTime(recordTime);
        recordInfo.setRecordName(recordName);
        recordInfo.setRecordDescribe(recordDescribe);
        recordInfo.setFileHash(FileUtils.uploadFile(file));
        Slf4j.logger.info("Controller:管理员添加档案信息" + recordInfo.toString());
        Integer result = adminInsertRecordInfoServiceImpl.adminInsertRecordInfoByRecordId(recordInfo);
        if (result.equals(EXECUTE_FAILED_NETWORK_CONNECTION)) {
            Slf4j.logger.info("Controller:添加失败 网络异常：" + recordInfo.toString());
            return new HttpResult("网络异常", EXECUTE_FAILED_NETWORK_CONNECTION);
        } else {
            Slf4j.logger.info("Controller:添加成功：" + recordInfo.toString());
            return new HttpResult("添加成功", result);
        }
    }

}
