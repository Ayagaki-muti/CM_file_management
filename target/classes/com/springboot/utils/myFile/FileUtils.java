package com.springboot.utils.myFile;

import com.springboot.utils.myLog.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.springboot.config.PathConf.recordFilePath;


public class FileUtils {

    /**
     * 上传MultipartFile文件
     * 成功：返回文件名,文件名为文件内容的hash值
     * 失败：返回error
     */
    public static String uploadFile(MultipartFile mFile) {
        try {
            InputStream inputStream = mFile.getInputStream();
            String[] fileName = mFile.getOriginalFilename().split("\\.");//获取文件名，并划分
            String uploadName = MD5Util.getFileMD5HashCode32ByInputStream(inputStream) + "." + fileName[1];//重新组合文件名
            FileUtils.upload(mFile.getBytes(), recordFilePath, uploadName);
            Slf4j.logger.info("获取到文件：" + uploadName);
            return uploadName;
        } catch (Exception e) {
            e.printStackTrace();
            return "文件上传失败！";
        }
    }

    /**
     * @param file     文件
     * @param filePath 文件存放路径
     * @param fileName 源文件名
     * @return
     */
    public static void upload(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {//若不存在
            targetFile.mkdirs();//创建
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);//写入
        out.flush();
        out.close();
    }

    public static synchronized String createtFileName() {
        Date dt = new Date(System.currentTimeMillis());
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String fileName = fmt.format(dt);
        return fileName;
    }
}
