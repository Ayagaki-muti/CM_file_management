package com.springboot.utils.myDate;

import com.springboot.utils.myLog.Slf4j;
import org.junit.platform.commons.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class DealDate {
    /**
     * 将2019-06-03T16:00:00.000Z日期格式转换为2019-06-03 16:00:00格式
     *
     * @param oldDateStr
     * @return
     */
    public static String dealDateFormat(String oldDateStr) {
        if (StringUtils.isBlank(oldDateStr)) {
            return null;
        }
        Date date, date1;
        String dateStr, result = null;
        try {
            dateStr = oldDateStr.replace("Z", " UTC");//是空格+UTC
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");
            date1 = df.parse(dateStr);
            SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date = df1.parse(date1.toString());
            //此处是将date类型装换为字符串类型，比如：Sat Nov 18 15:12:06 CST 2017转换为2017-11-18 15:12:06
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            result = sf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Slf4j.logger.info("函数返回值：" + result);
        return result;
    }

    /**
     * 将时间字符串 2020-01-01 12:00:00 转为Date类型
     */
    public static Date handleStringToDate1(String str) {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format1.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将时间字符串 2020-01-01 12:00 转为Date类型
     */
    public static Date handleStringToDate2(String str) {
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format1.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
