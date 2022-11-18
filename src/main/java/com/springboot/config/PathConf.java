package com.springboot.config;

/**
 * <h3>FileManage</h3>
 * <p>包含所有绝对路径 以便以后修改</p>
 *
 **/
public class PathConf {

    /**
     * 需要修改的内容，只需要修改秘钥，其他不需要修改
     * org1KeyFileName:变量值为文件名，文件位于"resources/file/fabric/crypto-config/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp/keystore"目录下
     * org2KeyFileName:变量值为文件名，文件位于"resources/file/fabric/crypto-config/peerOrganizations/org2.example.com/users/Admin@org2.example.com/msp/keystore"目录下
     */
    public static final String org1KeyFileName ="f0e764a6de6f7bfea3e112ae42705d98a3ba4a3393ffe94407d08ec93890e835_sk";
    public static final String org2KeyFileName ="63b061a93c8b177f63bb8e1c9503ec83e0f31552afaf0f576f206678e56f8cdd_sk";

    /**
     * 档案存放路径
     */
    // 本地测试
    public static final String recordFilePath = "src/main/resources/file/recordFile/";
    // 部署到服务器
//    public static final String recordFilePath = "/home/recordFile/";

    /**
     * Fabric公用路径
     */
    // 本地测试
    public static final String DIR = "src/main/resources/file/fabric/crypto-config/";
    // 部署到服务器
//    public static final String DIR = "/root/go/src/github.com/hyperledger/fabric-samples/first-network/crypto-config/";
    public static final String tlsOrderFilePath = DIR + "ordererOrganizations/example.com/tlsca/tlsca.example.com-cert.pem";

    /**
     * 以下是所有从ubuntu copy下来的文件位置和文件名
     * 用于配置SdkInitOrg1
     */
    public static final String org1KeyFolderPath = DIR + "peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp/keystore";
    public static final String org1CertFoldePath = DIR + "peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp/admincerts";
    public static final String org1CertFileName = "Admin@org1.example.com-cert.pem";
    public static final String org1TlsPeerFilePath = DIR + "peerOrganizations/org1.example.com/peers/peer0.org1.example.com/msp/tlscacerts/tlsca.org1.example.com-cert.pem";

    /**
     * 以下是所有从ubuntu copy下来的文件位置和文件名
     * 用于配置SdkInitOrg2
     */
    public static final String org2KeyFolderPath = DIR + "peerOrganizations/org2.example.com/users/Admin@org2.example.com/msp/keystore";
    public static final String org2CertFoldePath = DIR + "peerOrganizations/org2.example.com/users/Admin@org2.example.com/msp/admincerts";
    public static final String org2CertFileName = "Admin@org2.example.com-cert.pem";
    public static final String org2TlsPeerFilePath = DIR + "peerOrganizations/org2.example.com/tlsca/tlsca.org2.example.com-cert.pem";

    /**
     * Fabric升级需要的文件
     */
    public static final String fabricUpdateFIle = "src/main/resources/file/fabric/config/chaincodeendorsementpolicy.yaml";

    /**
     * 链码存放地址
     */
    public static final String chaincodeLocation = "src/main/resources/file/chaincode";



}
