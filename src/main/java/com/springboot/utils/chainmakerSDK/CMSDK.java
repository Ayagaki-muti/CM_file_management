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
import org.chainmaker.sdk.utils.SdkUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.springboot.config.PathConf.chaincodeLocation;
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
        ResultOuterClass.TxResponse responseInfo = null;
        try {
            //1.读取指定位置的文件
            byte[] byteCode = FileUtils.getResourceFileBytes(chaincodeLocation+'/'+contractName+".7z");
            //2.TODO:params不知道加什么，chaincodeVersion变成数字

            HashMap<String,byte[]> params_map = new HashMap<>();
           // params_map.put("save","".getBytes());
            //params_map.put("query","".getBytes());

            Request.Payload payload = chainClient.createContractCreatePayload(contractName, "1", byteCode,
                    ContractOuterClass.RuntimeType.DOCKER_GO, null);
            //3.使用多个支持者来创建合约
            Request.EndorsementEntry[] endorsementEntries = SdkUtils.getEndorsers(payload, new User[]{adminUser1, adminUser2, adminUser3});
            //4.发送请求(我把超时时间设置为官方推荐的10000的十倍)
            responseInfo = chainClient.sendContractManageRequest(payload, endorsementEntries, 10000, 10000);
            //5.
            if (responseInfo == null){
                return "合约安装失败";
            }
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
        //TODO:我感觉长安链不需要实例化,所以直接返回ok就行了大概
        return "init sucess";
    }

    /**
     * 合约升级
     * @param contractName 合约名字
     * @param chaincodeVersion 合约版本
     * @return
     */
    public static Object upgradeChaincode(String contractName, String chaincodeVersion) {
        ResultOuterClass.TxResponse responseInfo = null;
        try {
            //TODO:这个系统目前好像并不会升级合约,应该只有更新一下版本,但实际的合约内容没有输入
            byte[] byteCode = FileUtils.getResourceFileBytes(chaincodeLocation+'/'+contractName);
            //2.TODO:params不知道加什么,不知道要不要加原本的参数
            Request.Payload payload = chainClient.createContractUpgradePayload(contractName,chaincodeVersion,byteCode,
                                                                    ContractOuterClass.RuntimeType.DOCKER_GO, null);
            //3.使用多个支持者来支持升级合约
            Request.EndorsementEntry[] endorsementEntries = SdkUtils.getEndorsers(payload, new User[]{adminUser1, adminUser2, adminUser3});
            //4.发送请求(我把超时时间设置为官方推荐的10000的十倍)
            responseInfo = chainClient.sendContractManageRequest(payload, endorsementEntries, 100000, 100000);

            if (responseInfo == null){
                return "合约升级失败";
            }
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
        ResultOuterClass.TxResponse responseInfo = null;
        try {
            Map<String,byte[]> param = new HashMap<>();

            param.put("one",initArgs[0].getBytes());        //索引
            param.put("two",initArgs[1].getBytes());        //值

            responseInfo = chainClient.invokeContract(contractName,"save" ,
                    null, param,100000, 100000);
            //TODO:判断responseInfo中的信息,看它是不是成功了
            //TODO:看看输出的格式是不是必须得这样标准的格式result = "{" + response.getPeer().getName() + "} invoke proposal {" + funcName + "} sucess";
            //System.out.println("CM_Terminal_log:"+responseInfo);
            //原本程序中:
//            if (response.getStatus().getStatus() == 200) {
//                log.info("{} invoke proposal {} sucess", response.getPeer().getName(), funcName);
//                result = "{" + response.getPeer().getName() + "} invoke proposal {" + funcName + "} sucess";
//            } else {
//                String[] logArgs = {response.getMessage(), funcName, response.getPeer().getName()};
//                log.error("{} invoke proposal {} fail on {}", logArgs);
//                result = "{" + logArgs[0] + "} invoke proposal {" + logArgs[1] + "} fail on {" + logArgs[2] + "}";
//            }
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
        ResultOuterClass.TxResponse responseInfo = null;

        try {
            Map<String,byte[]> param = new HashMap<>();
            for (String i :initArgs){
                param.put("",i.getBytes());         //TODO:不知道参数写啥
            }
            //TODO:method-QUERY_CONTRACT_METHOD需要替换,参数也需要修改
            responseInfo = chainClient.queryContract(contractName, "QUERY_CONTRACT_METHOD",
                    null,  param,100000);

            Map map = null;
            //TODO:在map中放入responseInfo中获取到的数据

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
        ResultOuterClass.TxResponse responseInfo = null;

        try {
            Map<String,byte[]> param = new HashMap<>();
            for (String i :initArgs){
                param.put("",i.getBytes());         //TODO:不知道参数写啥
            }
            //TODO:method-QUERY_ALL_CONTRACT_METHOD需要替换,参数也需要修改
            responseInfo = chainClient.queryContract(contractName, "QUERY_ALL_CONTRACT_METHOD",
                    null,  param,10000);

            Map result_map = null;
            //TODO:在map中放入responseInfo中获取到的数据

            return result_map.values();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
