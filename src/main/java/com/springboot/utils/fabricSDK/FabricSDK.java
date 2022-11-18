package com.springboot.utils.fabricSDK;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.TransactionRequest;
import org.hyperledger.fabric.sdk.exception.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.springboot.config.PathConf.*;


/**
 * FabricSDK：在以后使用的时候直接实例化即可
 *
 */
public class FabricSDK {

    /**
     * chainCodeName ： 合约名称
     */
    private String chainCodeName;

    public FabricSDK(String chainCodeName) {
        this.chainCodeName = chainCodeName;
    }

    /**
     * 安装chaincode
     */
    public static Object installChaincode(String chaincodeVersion, String chaincodeLocation, String chaincodeName) {
        String installOrg1 = SdkInitOrg1.install(chaincodeVersion, chaincodeLocation, chaincodeName).toString();
        String installOrg2 = SdkInitOrg2.install(chaincodeVersion, chaincodeLocation, chaincodeName).toString();
        if ("true".equals(installOrg1) && "true".equals(installOrg2)) {
            return "success";
        } else {
            return installOrg1 + "\n" + installOrg2;
        }
    }

    /**
     * 合约实例化
     */
        public static Object instantiated(String chaincodeName, String chaincodeVersion) {
        try {
            UserContext userContext = new UserContext();//设置环境变量
            userContext.setAffiliation("Org1");
            userContext.setMspId("Org1MSP");
            userContext.setAccount("李伟");
            userContext.setName("admin");
            Enrollment enrollment = UserUtils.getEnrollment(org1KeyFolderPath, org1KeyFileName, org1CertFoldePath, org1CertFileName);
            userContext.setEnrollment(enrollment);
            FabricClient fabricClient = new FabricClient(userContext);
            Peer peer = fabricClient.getPeer("peer0.org1.example.com", "grpcs://peer0.org1.example.com:7051", org1TlsPeerFilePath);
            Orderer order = fabricClient.getOrderer("orderer.example.com", "grpcs://orderer.example.com:7050", tlsOrderFilePath);
            String[] initArgs = {""};
            return fabricClient.initChaincode("mychannel", TransactionRequest.Type.GO_LANG, chaincodeName, chaincodeVersion, order, peer, "init", initArgs);
        } catch (NoSuchMethodException e) {
            return e.toString();
        } catch (NoSuchAlgorithmException e) {
            return e.toString();
        } catch (InstantiationException e) {
            return e.toString();
        } catch (CryptoException e) {
            return e.toString();
        } catch (ClassNotFoundException e) {
            return e.toString();
        } catch (InvalidArgumentException e) {
            return e.toString();
        } catch (InvalidKeySpecException e) {
            return e.toString();
        } catch (org.bouncycastle.crypto.CryptoException e) {
            return e.toString();
        } catch (IllegalAccessException e) {
            return e.toString();
        } catch (ProposalException e) {
            return e.toString();
        } catch (InvocationTargetException e) {
            return e.toString();
        } catch (IOException e) {
            return e.toString();
        } catch (TransactionException e) {
            return e.toString();
        }
    }

    /**
     * 合约升级(出现错误：ERROR [cn.com.fabric.sdk.FabricClient] cannot get package for chaincode (admin:2.0) upgrade fail
     */
    public static Object upgradeChaincode(String chaincodeName, String chaincodeVersion) {
        try {
            UserContext userContext = new UserContext();
            userContext.setAffiliation("Org1");
            userContext.setMspId("Org1MSP");
            userContext.setAccount("李伟");
            userContext.setName("admin");
            Enrollment enrollment = UserUtils.getEnrollment(org1KeyFolderPath, org1KeyFileName, org1CertFoldePath, org1CertFileName);
            userContext.setEnrollment(enrollment);
            FabricClient fabricClient = new FabricClient(userContext);
            Peer peer = fabricClient.getPeer("peer0.org1.example.com", "grpcs://peer0.org1.example.com:7051", org1TlsPeerFilePath);
            Orderer order = fabricClient.getOrderer("orderer.example.com", "grpcs://orderer.example.com:7050", tlsOrderFilePath);
            String[] initArgs = {""};
            return fabricClient.upgradeChaincode("mychannel", TransactionRequest.Type.GO_LANG, chaincodeName, chaincodeVersion, order, peer, "init", initArgs);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return e.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return e.toString();
        } catch (InstantiationException e) {
            e.printStackTrace();
            return e.toString();
        } catch (CryptoException e) {
            e.printStackTrace();
            return e.toString();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return e.toString();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            return e.toString();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return e.toString();
        } catch (org.bouncycastle.crypto.CryptoException e) {
            e.printStackTrace();
            return e.toString();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return e.toString();
        } catch (ProposalException e) {
            e.printStackTrace();
            return e.toString();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } catch (TransactionException e) {
            e.printStackTrace();
            return e.toString();
        } catch (ChaincodeEndorsementPolicyParseException e) {
            e.printStackTrace();
            return e.toString();
        }
    }

    /**
     * invoke 合约
     * invoke(initArgsInvokeRecord,chaincodeName4);
     * @return Object
     */
    public boolean invoke(String[] initArgs) {
        try {
            UserContext userContext = new UserContext();
            userContext.setAffiliation("Org1");
            userContext.setMspId("Org1MSP");
            userContext.setAccount("李伟");
            userContext.setName("admin");
            Enrollment enrollment = UserUtils.getEnrollment(org1KeyFolderPath, org1KeyFileName, org1CertFoldePath, org1CertFileName);
            userContext.setEnrollment(enrollment);
            FabricClient fabricClient = new FabricClient(userContext);
            Peer peer0 = fabricClient.getPeer("peer0.org1.example.com", "grpcs://peer0.org1.example.com:7051", org1TlsPeerFilePath);
            Peer peer1 = fabricClient.getPeer("peer0.org2.example.com", "grpcs://peer0.org2.example.com:9051", org2TlsPeerFilePath);
            List<Peer> peers = new ArrayList<>();
            peers.add(peer0);
            peers.add(peer1);
            Orderer order = null;
            order = fabricClient.getOrderer("orderer.example.com", "grpcs://orderer.example.com:7050", tlsOrderFilePath);
            fabricClient.invoke("mychannel", TransactionRequest.Type.GO_LANG, chainCodeName, order, peers, "save", initArgs);
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return false;
        } catch (CryptoException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            return false;
        } catch (org.bouncycastle.crypto.CryptoException e) {
            e.printStackTrace();
            return false;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (ProposalException e) {
            e.printStackTrace();
            return false;
        } catch (TransactionException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 查询最新合约
     * String[] initArgsQuery1 = {"10001"};
     * queryAllChaincode(initArgsQuery10003,chaincodeName3)
     */
    public Collection queryChaincode(String[] initArgs) {
        try {
            UserContext userContext = new UserContext();
            userContext.setAffiliation("Org1");
            userContext.setMspId("Org1MSP");
            userContext.setAccount("李伟");
            userContext.setName("adminController");
            Enrollment enrollment = null;
            enrollment = UserUtils.getEnrollment(org1KeyFolderPath, org1KeyFileName, org1CertFoldePath, org1CertFileName);
            userContext.setEnrollment(enrollment);
            FabricClient fabricClient = new FabricClient(userContext);
            Peer peer0 = fabricClient.getPeer("peer0.org1.example.com", "grpcs://peer0.org1.example.com:7051", org1TlsPeerFilePath);
            Peer peer1 = fabricClient.getPeer("peer0.org2.example.com", "grpcs://peer0.org2.example.com:9051", org2TlsPeerFilePath);
            List<Peer> peers = new ArrayList<>();
            peers.add(peer0);
            peers.add(peer1);
            Map map = fabricClient.queryChaincode(peers, "mychannel", TransactionRequest.Type.GO_LANG, chainCodeName, "query", initArgs);
            return map.values();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (org.bouncycastle.crypto.CryptoException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (CryptoException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (ProposalException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询所有合约
     * queryAllChaincode(initArgsQuery10003,chaincodeName4)
     *
     * @return
     */
    public Collection queryAllChaincode(String[] initArgs) {
        try {
            UserContext userContext = new UserContext();
            userContext.setAffiliation("Org1");
            userContext.setMspId("Org1MSP");
            userContext.setAccount("李伟");
            userContext.setName("adminController");
            Enrollment enrollment = null;
            enrollment = UserUtils.getEnrollment(org1KeyFolderPath, org1KeyFileName, org1CertFoldePath, org1CertFileName);
            userContext.setEnrollment(enrollment);
            FabricClient fabricClient = new FabricClient(userContext);
            Peer peer0 = fabricClient.getPeer("peer0.org1.example.com", "grpcs://peer0.org1.example.com:7051", org1TlsPeerFilePath);
            Peer peer1 = fabricClient.getPeer("peer0.org2.example.com", "grpcs://peer0.org2.example.com:9051", org2TlsPeerFilePath);
            List<Peer> peers = new ArrayList<>();
            peers.add(peer0);
            peers.add(peer1);
            Map map = fabricClient.queryChaincode(peers, "mychannel", TransactionRequest.Type.GO_LANG, chainCodeName, "queryHistory", initArgs);
            return map.values();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (org.bouncycastle.crypto.CryptoException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (CryptoException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        } catch (ProposalException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (TransactionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
