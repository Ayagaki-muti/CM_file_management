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

import java.io.InputStream;
import java.util.*;

import static com.springboot.utils.chainmakerSDK.StaticConfig.*;

public class CMSDK {
    static ChainClient chainClient;
    static ChainManager chainManager;


    /**
     * 初始化长安链
     */
    public CMSDK() {
        try {
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
     * @param contractName 合约名称
     * @return
     */
    public static Object installChaincode(String chaincodeVersion, String chaincodeLocation, String contractName){
        try {
            //可以通过运行起来，grep来过滤
            System.out.println("CM_Terminal_log:"+chaincodeLocation+"/"+contractName);
            byte[] byteCode = FileUtils.getResourceFileBytes(chaincodeLocation+'/'+contractName);
            Request.Payload payload = chainClient.createContractCreatePayload(contractName, "1", byteCode,
                    ContractOuterClass.RuntimeType.DOCKER_GO, null);

        }catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }
        return null;
    }

    /**
     * 合约实例化
     * @param contractName 合约名字
     * @param chaincodeVersion 合约版本
     * @return
     */
    public static Object instantiated(String contractName, String chaincodeVersion) {
        try {


        }catch (Exception e){
            e.printStackTrace();
            return e.toString();
        }
        return null;
    }

    /**
     * 合约升级
     * @param contractName 合约名字
     * @param chaincodeVersion 合约版本
     * @return
     */
    public static Object upgradeChaincode(String contractName, String chaincodeVersion) {
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
    public boolean invoke(String contractName, String[] initArgs) {
        try {
            Map<String,byte[]> param = new HashMap<>();
            for (String i :initArgs){
                param.put("",i.getBytes());         //TODO:不知道参数写啥
            }
            ResultOuterClass.TxResponse responseInfo = null;
            responseInfo = chainClient.invokeContract(contractName,"save" ,
                    null, param,10000, 10000);
            System.out.println("CM_Terminal_log:"+responseInfo);

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 调用某个合约里的《查询最新》方法
     * @param initArgs 传参
     * @return
     */
    public Collection queryChaincode(String contractName, String[] initArgs) {
        try {


            Map map = null;
            return map.values();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 调用某个合约中的《查询全部》方法
     * @param initArgs 传参
     * @return
     */
    public Collection queryAllChaincode(String contractName, String[] initArgs){
        try {


            Map map = null;
            return map.values();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
