package com.springboot.utils.chainmakerSDK;

import com.springboot.ididi;
import org.chainmaker.pb.common.ContractOuterClass;
import org.chainmaker.pb.common.Request;
import org.chainmaker.pb.common.ResultOuterClass;
import org.chainmaker.sdk.ChainClient;
import org.chainmaker.sdk.ChainManager;
import org.chainmaker.sdk.User;
import org.chainmaker.sdk.config.NodeConfig;
import org.chainmaker.sdk.config.SdkConfig;
import org.chainmaker.sdk.utils.FileUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class CMSDK {
    static String SDK_CONFIG_PATH = "crypto-config/sdk_config.yml";
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

    private String chainCodeName; //智能合约名字
    private static final String CONTRACT_NAME = "mychannel";

    static ChainClient chainClient;
    static ChainManager chainManager;
    static User adminUser1;
    static User adminUser2;
    static User adminUser3;
    static User adminUser4;

    /**
     * 初始化长安链
     * @param chainCodeName 智能合约名称
     */
    public CMSDK(String chainCodeName) {
        try {
            this.chainCodeName = chainCodeName;
            SdkConfig sdkConfig;
            if(adminUser1 == null){
                adminUser1 = new User(ORG_ID1, FileUtils.getResourceFileBytes(ADMIN1_KEY_PATH),
                        FileUtils.getResourceFileBytes(ADMIN1_CERT_PATH),
                        FileUtils.getResourceFileBytes(ADMIN1_TLS_KEY_PATH),
                        FileUtils.getResourceFileBytes(ADMIN1_TLS_CERT_PATH));
            }
            if(adminUser2 == null){
                adminUser2 = new User(ORG_ID2, FileUtils.getResourceFileBytes(ADMIN2_KEY_PATH),
                        FileUtils.getResourceFileBytes(ADMIN2_CERT_PATH),
                        FileUtils.getResourceFileBytes(ADMIN2_TLS_KEY_PATH),
                        FileUtils.getResourceFileBytes(ADMIN2_TLS_CERT_PATH));
            }
            if(adminUser3 == null){
                adminUser3 = new User(ORG_ID3, FileUtils.getResourceFileBytes(ADMIN3_KEY_PATH),
                        FileUtils.getResourceFileBytes(ADMIN3_CERT_PATH),
                        FileUtils.getResourceFileBytes(ADMIN3_TLS_KEY_PATH),
                        FileUtils.getResourceFileBytes(ADMIN3_TLS_CERT_PATH));
            }
            if(adminUser4 == null){
                adminUser4 = new User(ORG_ID4, FileUtils.getResourceFileBytes(ADMIN4_KEY_PATH),
                        FileUtils.getResourceFileBytes(ADMIN4_CERT_PATH),
                        FileUtils.getResourceFileBytes(ADMIN4_TLS_KEY_PATH),
                        FileUtils.getResourceFileBytes(ADMIN4_TLS_CERT_PATH));
            }
            if(chainManager == null){
                Yaml yaml = new Yaml();
                InputStream in = ididi.class.getClassLoader().getResourceAsStream(SDK_CONFIG_PATH);
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
            }
            if(chainClient == null){
                Yaml yaml = new Yaml();
                InputStream in = ididi.class.getClassLoader().getResourceAsStream(SDK_CONFIG_PATH);
                sdkConfig = yaml.loadAs(in, SdkConfig.class);
                assert in != null;
                in.close();
                chainClient = chainManager.getChainClient(sdkConfig.getChainClient().getChainId());

                if (chainClient == null) {
                    chainClient = chainManager.createChainClient(sdkConfig);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 安装智能合约
     * @param chaincodeVersion 合约版本
     * @param chaincodeLocation 合约位置
     * @param chaincodeName 合约名称
     * @return
     */
    public static Object installChaincode(String chaincodeVersion, String chaincodeLocation, String chaincodeName){
        try {
            //可以通过运行起来，grep来过滤
            System.out.println("CM_Terminal_log:"+chaincodeLocation+"/"+chaincodeName);
            byte[] byteCode = FileUtils.getResourceFileBytes(chaincodeLocation+'/'+chaincodeName);
            Request.Payload payload = chainClient.createContractCreatePayload(chaincodeName, "1", byteCode,
                    ContractOuterClass.RuntimeType.DOCKER_GO, null);

        }catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }
        return null;
    }

    /**
     * 合约实例化
     * @param chaincodeName 合约名字
     * @param chaincodeVersion 合约版本
     * @return
     */
    public static Object instantiated(String chaincodeName, String chaincodeVersion) {
        try {


        }catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }
        return null;
    }

    /**
     * 合约升级
     * @param chaincodeName 合约名字
     * @param chaincodeVersion 合约版本
     * @return
     */
    public static Object upgradeChaincode(String chaincodeName, String chaincodeVersion) {
        try {


        }catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }
        return null;
    }

    /**
     * 合约执行
     * @param initArgs 传参
     * @return
     */
    public boolean invoke(String[] initArgs) {
        try {
            Map<String,byte[]> param = new HashMap<>();
            for (String i :initArgs){
                param.put("",i.getBytes());     //TODO:不知道参数写啥
            }
            ResultOuterClass.TxResponse responseInfo = null;
            responseInfo = chainClient.invokeContract(CONTRACT_NAME,"save" ,
                    null, param,10000, 10000);
            System.out.println("CM_Terminal_log:"+responseInfo);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 合约查询（最新）
     * @param initArgs 传参
     * @return
     */
    public Collection queryChaincode(String[] initArgs) {
        try {


            Map map = null;
            return map.values();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 查询所有合约
     * @param initArgs 传参
     * @return
     */
    public Collection queryAllChaincode(String[] initArgs){
        try {


            Map map = null;
            return map.values();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
