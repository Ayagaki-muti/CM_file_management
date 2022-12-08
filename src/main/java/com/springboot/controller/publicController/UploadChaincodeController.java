package com.springboot.controller.publicController;

import com.springboot.utils.chainmakerSDK.CMSDK;
import com.springboot.utils.myHttpResult.HttpResult;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static com.springboot.config.PathConf.chaincodeLocation;

@Controller
/**
 * 上传、实例化链码控制层
 * */
@RequestMapping("admin")
public class UploadChaincodeController {

    /**
     * POST上传链码
     */
    @PostMapping("uploadChaincode")
    @ResponseBody
    public Object uploadChaincode(@RequestParam("chaincodeName") String chaincodeName,
                                  @RequestParam("chaincodeVersion") String chaincodeVersion) {
        Slf4j.logger.info("安装链码:" + chaincodeName);
        return new HttpResult(CMSDK.installChaincode(chaincodeVersion, chaincodeLocation, chaincodeName).toString(), 200);
    }

    /**
     * POST实例化链码
     */
    @PostMapping("instantiatedChaincode")
    @ResponseBody
    public Object instantiatedChaincode(@RequestParam("chaincodeName") String chaincodeName,
                                        @RequestParam("chaincodeVersion") String chaincodeVersion) {
        Slf4j.logger.info("实例化链码:" + chaincodeName);
        return new HttpResult(CMSDK.instantiated(chaincodeName, chaincodeVersion).toString(), 200);
    }

    /**
     * POST升级链码
     */
    @PostMapping("upgradeChaincode")
    @ResponseBody
    public Object upgradeChaincode(@RequestParam("chaincodeName") String chaincodeName,
                                   @RequestParam("chaincodeVersion") String chaincodeVersion) {
        Slf4j.logger.info("升级链码:" + chaincodeName);
        return new HttpResult(CMSDK.upgradeChaincode(chaincodeName, chaincodeVersion).toString(), 200);
    }

}
