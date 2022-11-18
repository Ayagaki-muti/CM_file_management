package com.springboot.controller.adminController.departInfo;

import com.springboot.entity.DepartInfo;
import com.springboot.service.admin.departInfo.AdminInsertDepartInfoService;
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
 * 管理员注册控制层
 * */
@RequestMapping("admin")
public class AdminInsertDepartInfoController {

    @Autowired
    private AdminInsertDepartInfoService adminInsertDepartInfoServiceImpl;

    /**
     * POST接收更新信息
     */
    @PostMapping("insertDepartInfo")
    @ResponseBody
    public Object insertDepartInfo(@RequestParam("departName") String departName, @RequestParam("departSuperior") String departSuperior) {
        DepartInfo departInfo = new DepartInfo();
        departInfo.setDepartSuperior(departSuperior);
        departInfo.setDepartName(departName);
        Slf4j.logger.info("Controller:管理员添加部门信息" + departInfo.toString());
        Integer result = adminInsertDepartInfoServiceImpl.adminInsertDepartInfo(departInfo);
        if (result.equals(EXECUTE_FAILED_NETWORK_CONNECTION)) {
            Slf4j.logger.info("Controller:添加失败 网络异常：" + departInfo.toString());
            return new HttpResult("网络异常", EXECUTE_FAILED_NETWORK_CONNECTION);
        } else {
            Slf4j.logger.info("Controller:添加成功：" + departInfo.toString());
            return new HttpResult("添加成功");
        }
    }

}
