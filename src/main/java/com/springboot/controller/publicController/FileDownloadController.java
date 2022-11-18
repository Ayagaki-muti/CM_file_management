package com.springboot.controller.publicController;

import com.springboot.utils.myFile.MD5Util;
import com.springboot.utils.myLog.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static com.springboot.config.PathConf.recordFilePath;

@Controller
@ResponseBody
public class FileDownloadController {


    @RequestMapping("downloadRecord/{fileName}")
    public Object downloadFile(HttpServletResponse response, @PathVariable String fileName) {
        Slf4j.logger.info("开始下载文件" + recordFilePath + fileName);
        if (fileName != null) {
            //设置文件路径
            File file = new File(recordFilePath, fileName);
            if (file.exists()) {
//                if(1==1) {
                    try {
                            //校验hash
                            InputStream inputStream = new FileInputStream(file);
                            String fileHash = MD5Util.getFileMD5HashCode32ByInputStream(inputStream);
                            String name = fileName.substring(0,fileName.lastIndexOf("."));
                            if(!fileHash.equals(name)){
                                return "文件已发生篡改！";
                            }
                        //return fileHash;
                    } catch (Exception e) {
                        return e.toString();
                    }
//                }else{
                // 设置强制下载不打开
                response.setContentType("application/force-download");
                // 设置文件名，下载
                    response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
//        }
        }
        return null;
    }

}
