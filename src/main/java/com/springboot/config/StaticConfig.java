package com.springboot.config;

import java.util.concurrent.TimeUnit;

/**
 * <h3>FileManage</h3>
 * <p>静态全局配置</p>
 *

 **/
public class StaticConfig {

    /**
     * 账号初始值
     */
    public static String INITIAL_VALUE_ADMINID = "10000";
    public static String INITIAL_VALUE_RECORDADMINID = "10000";
    public static String INITIAL_VALUE_RECORDAUTHID = "10000";
    public static String INITIAL_VALUE_RECORDID = "10000";
    public static String INITIAL_VALUE_DEPARTID = "10000";
    public static String INITIAL_VALUE_USERID = "10000";

    /**
     * 执行成功向前端的返回值
     */
    public static Integer EXECUTE_SUCCESS_CODE_FRONT = 200;

    /**
     * 执行成功：用户数据错误返回值
     */
    public static Integer EXECUTE_FAILED_USER_DATA = 201;


    /**
     * 执行失败：区块链操作失败返回值
     */
    public static Integer EXECUTE_FAILED_BLOCK_CHAIN_OPERATION = 401;


    /**
     * 执行失败：SpringBoot后台执行失败返回值
     */
    public static Integer EXECUTE_FAILED_SPRINGBOOT = 402;

    /**
     * 执行失败：网络连接失败败返回值
     */
    public static Integer EXECUTE_FAILED_NETWORK_CONNECTION = 403;

    /**
     * Redis数据有效时间以及单位 -1L代表永不过期
     */
    public static Long REDIS_EFFECTIVE_TIME = -1L;
    public static TimeUnit REDIS_EFFECTIVE_TIMEUNIT = TimeUnit.SECONDS;

    /**
     * 数据插入后redis延迟同步时间
     */
    public static Integer REDIS_DELAY_SYN_TIME_AFTER_DATA_INSERTION_MS = 5000;

    /**
     * 系统管理员操作时若需要输入档案管理员ID可以使用以下变量
     */
    public static String RECORD_ADMINID_FOR_ADMIN = "1";


}
