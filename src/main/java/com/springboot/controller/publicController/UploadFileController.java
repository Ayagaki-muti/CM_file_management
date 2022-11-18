package com.springboot.controller.publicController;

import com.springboot.utils.myFile.FileUtils;
import com.springboot.utils.myHttpResult.HttpResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件返回文件hash值
 *

 **/
@Controller
public class UploadFileController {

    @ResponseBody
    @PostMapping("uploadFile")
    public Object uploadFile(@Param("file") MultipartFile file) {
        return new HttpResult(FileUtils.uploadFile(file));
    }

}
