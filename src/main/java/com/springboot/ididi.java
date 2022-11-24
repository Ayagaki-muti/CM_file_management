package com.springboot;

import org.chainmaker.pb.config.ChainConfigOuterClass;
import org.chainmaker.sdk.ChainClient;
import org.chainmaker.sdk.ChainManager;
import org.chainmaker.sdk.User;
import org.chainmaker.sdk.config.NodeConfig;
import org.chainmaker.sdk.config.SdkConfig;
import org.chainmaker.sdk.utils.FileUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ididi {

    static final String ADMIN1_KEY_PATH = "crypto-config/TestCMorg1/user/admin1/示例测试用户1.sign.key";
    static final String ADMIN1_CERT_PATH = "crypto-config/TestCMorg1/user/admin1/示例测试用户1.sign.crt";
    static final String ADMIN2_KEY_PATH = "crypto-config/TestCMorg2/user/admin1/示例测试用户2.sign.key";
    static final String ADMIN2_CERT_PATH = "crypto-config/TestCMorg2/user/admin1/示例测试用户2.sign.crt";
    static final String ADMIN3_KEY_PATH = "crypto-config/TestCMorg3/node/consesus1/示例测试节点3.sign.key";
    static final String ADMIN3_CERT_PATH = "crypto-config/TestCMorg3/node/consesus1/示例测试节点3.sign.crt";

    static final String ADMIN4_KEY_PATH = "crypto-config/TestCMorg4/node/consesus1/示例测试节点4.sign.key";
    static final String ADMIN4_CERT_PATH = "crypto-config/TestCMorg4/node/consesus1/示例测试节点4.sign.crt";

    static String ADMIN1_TLS_KEY_PATH = "crypto-config/TestCMorg1/user/admin1/示例测试用户1.tls.key";
    static String ADMIN1_TLS_CERT_PATH = "crypto-config/TestCMorg1/user/admin1/示例测试用户1.tls.crt";
    static String ADMIN2_TLS_KEY_PATH = "crypto-config/TestCMorg2/user/admin1/示例测试用户2.tls.key";
    static String ADMIN2_TLS_CERT_PATH = "crypto-config/TestCMorg2/user/admin1/示例测试用户2.tls.crt";
    static String ADMIN3_TLS_KEY_PATH = "crypto-config/TestCMorg3/user/admin1/示例测试用户3.tls.key";
    static String ADMIN3_TLS_CERT_PATH = "crypto-config/TestCMorg3/user/admin1/示例测试用户3.tls.crt";

    static String ADMIN4_TLS_KEY_PATH = "crypto-config/TestCMorg4/user/admin1/示例测试用户4.tls.key";

    static String ADMIN4_TLS_CERT_PATH = "crypto-config/TestCMorg4/user/admin1/示例测试用户4.tls.crt";

    static final String ORG_ID1 = "TestCMorg1";
    static final String ORG_ID2 = "TestCMorg2";
    static final String ORG_ID3 = "TestCMorg3";
    static final String ORG_ID4 = "TestCMorg4";

    static String SDK_CONFIG = "crypto-config/sdk_config.yml";

    static ChainClient chainClient;
    static ChainManager chainManager;
    static User adminUser1;
    static User adminUser2;
    static User adminUser3;

    static User adminUser4;

    public static void main(String[] args) throws Exception{
        Yaml yaml = new Yaml();
        InputStream in = ididi.class.getClassLoader().getResourceAsStream(SDK_CONFIG);

        SdkConfig sdkConfig;
        sdkConfig = yaml.loadAs(in, SdkConfig.class);
        assert in != null;
        in.close();

        for (NodeConfig nodeConfig : sdkConfig.getChainClient().getNodes()) {
            List<byte[]> tlsCaCertList = new ArrayList<>();
            if (nodeConfig.getTrustRootPaths() != null) {
                for (String rootPath : nodeConfig.getTrustRootPaths()) {
                    List<String> filePathList = FileUtils.getFilesByPath(rootPath);
                    for (String filePath : filePathList) {
                        tlsCaCertList.add(FileUtils.getFileBytes(filePath));
                    }
                }
            }
            byte[][] tlsCaCerts = new byte[tlsCaCertList.size()][];
            tlsCaCertList.toArray(tlsCaCerts);
            nodeConfig.setTrustRootBytes(tlsCaCerts);
        }

        chainManager = ChainManager.getInstance();
        chainClient = chainManager.getChainClient(sdkConfig.getChainClient().getChainId());

        if (chainClient == null) {
            chainClient = chainManager.createChainClient(sdkConfig);
        }

        adminUser1 = new User(ORG_ID1, FileUtils.getResourceFileBytes(ADMIN1_KEY_PATH),
                FileUtils.getResourceFileBytes(ADMIN1_CERT_PATH),
                FileUtils.getResourceFileBytes(ADMIN1_TLS_KEY_PATH),
                FileUtils.getResourceFileBytes(ADMIN1_TLS_CERT_PATH));

        adminUser2 = new User(ORG_ID2, FileUtils.getResourceFileBytes(ADMIN2_KEY_PATH),
                FileUtils.getResourceFileBytes(ADMIN2_CERT_PATH),
                FileUtils.getResourceFileBytes(ADMIN2_TLS_KEY_PATH),
                FileUtils.getResourceFileBytes(ADMIN2_TLS_CERT_PATH));

        adminUser3 = new User(ORG_ID3, FileUtils.getResourceFileBytes(ADMIN3_KEY_PATH),
                FileUtils.getResourceFileBytes(ADMIN3_CERT_PATH),
                FileUtils.getResourceFileBytes(ADMIN3_TLS_KEY_PATH),
                FileUtils.getResourceFileBytes(ADMIN3_TLS_CERT_PATH));

        adminUser4 = new User(ORG_ID4, FileUtils.getResourceFileBytes(ADMIN4_KEY_PATH),
                FileUtils.getResourceFileBytes(ADMIN4_CERT_PATH),
                FileUtils.getResourceFileBytes(ADMIN4_TLS_KEY_PATH),
                FileUtils.getResourceFileBytes(ADMIN4_TLS_CERT_PATH));

        ChainConfigOuterClass.ChainConfig chainConfig = null;
        try {
            chainConfig = ididi.chainClient.getChainConfig(20000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(chainConfig.toString());
    }
}
