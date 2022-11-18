package com.springboot.utils.myDate;

import com.springboot.utils.myLog.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <h3>AutoMall</h3>
 * <p>生成当前时间</p>
 *
 **/
public class NowDateTime {
    /**
     * 生成当前时间：2020-03-27 20:38:44
     */
    public static String getNowTime() {
        //使用Date
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Slf4j.logger.info("当前时间：" + sdf.format(d));
        return sdf.format(d);
    }
}
