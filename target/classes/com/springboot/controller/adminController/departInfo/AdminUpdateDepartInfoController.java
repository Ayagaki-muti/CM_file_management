package com.springboot.controller.adminController.departInfo;

import com.springboot.entity.DepartInfo;
import com.springboot.service.admin.departInfo.AdminBrowseDepartInfoService;
import com.springboot.service.admin.departInfo.AdminUpdateDepartInfoService;
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
 * 用户登录控制层
 * */
@RequestMapping("admin")
public class AdminUpdateDepartInfoController {

    @Autowired
    private AdminBrowseDepartInfoService adminBrowseDepartInfoServiceImpl;
    @Autowired
    private AdminUpdateDepartInfoService adminUpdateDepartInfoServiceImpl;

    @GetMapping("updateDepartInfo")
    public String updateDepartInfo(@Param("departId") String departId, Model model) {
        Map<String, Object> departInfo = adminBrowseDepartInfoServiceImpl.adminBrowseDepartInfo(departId);
        Slf4j.logger.info(departInfo.toString());
        model.addAttribute("departInfo", departInfo);
        return "admin/departInfo/updateDepartInfo";
    }

    /**
     * POST接收更新信息
     */
    @PostMapping("updateDepartInfo")
    @ResponseBody
    public Object updateDepartInfo(@RequestParam("departId") String departId, @RequestParam("departName") String departName,
                                   @RequestParam("departSuperior") String departSuperior) {
        DepartInfo departInfo = new DepartInfo();
        departInfo.setDepartId(departId);
        departInfo.setDepartName(departName);
        departInfo.setDepartSuperior(departSuperior);
        Slf4j.logger.info("Controller:管理员更新部门信息" + departInfo.toString());
        Integer result = adminUpdateDepartInfoServiceImpl.adminUpdateDepartInfo(departInfo);
        if (result.equals(EXECUTE_SUCCESS_CODE_FRONT)) {
            Slf4j.logger.info("Controller:更新成功：" + departInfo.toString());
            return new HttpResult("更新成功", EXECUTE_SUCCESS_CODE_FRONT);
        } else if (result.equals(EXECUTE_FAILED_NETWORK_CONNECTION)) {
            Slf4j.logger.info("Controller:添加失败 网络异常：" + departInfo.toString());
            return new HttpResult("网络异常", EXECUTE_FAILED_NETWORK_CONNECTION);
        }
        return null;
    }

}
