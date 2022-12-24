package com.springboot.utils.chainmakerSDK;

import org.chainmaker.sdk.User;

public class StaticConfig {
    static String SDK_CONFIG_PATH = "crypto-config/sdk_config.yml";
    static final String ADMIN1_KEY_PATH = "crypto-config/TestCMorg1/user/示例测试用户1/示例测试用户1.sign.key";
    static final String ADMIN1_CERT_PATH = "crypto-config/TestCMorg1/user/示例测试用户1/示例测试用户1.sign.crt";
    static final String ADMIN2_KEY_PATH = "crypto-config/TestCMorg2/user/示例测试用户2/示例测试用户2.sign.key";
    static final String ADMIN2_CERT_PATH = "crypto-config/TestCMorg2/user/示例测试用户2/示例测试用户2.sign.crt";
    static final String ADMIN3_KEY_PATH = "crypto-config/TestCMorg3/user/示例测试用户3/示例测试用户3.sign.key";
    static final String ADMIN3_CERT_PATH = "crypto-config/TestCMorg3/user/示例测试用户3/示例测试用户3.sign.crt";


    static String ADMIN1_TLS_KEY_PATH = "crypto-config/TestCMorg1/user/示例测试用户1/示例测试用户1.tls.key";
    static String ADMIN1_TLS_CERT_PATH = "crypto-config/TestCMorg1/user/示例测试用户1/示例测试用户1.tls.crt";
    static String ADMIN2_TLS_KEY_PATH = "crypto-config/TestCMorg2/user/示例测试用户2/示例测试用户2.tls.key";
    static String ADMIN2_TLS_CERT_PATH = "crypto-config/TestCMorg2/user/示例测试用户2/示例测试用户2.tls.crt";
    static String ADMIN3_TLS_KEY_PATH = "crypto-config/TestCMorg3/user/示例测试用户3/示例测试用户3.tls.key";
    static String ADMIN3_TLS_CERT_PATH = "crypto-config/TestCMorg3/user/示例测试用户3/示例测试用户3.tls.crt";

    static final String ORG_ID1 = "TestCMorg1";
    static final String ORG_ID2 = "TestCMorg2";
    static final String ORG_ID3 = "TestCMorg3";

    static User adminUser1;
    static User adminUser2;
    static User adminUser3;
}
