package com.springboot.utils.chainmakerSDK;

import com.springboot.utils.fabricSDK.UserContext;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.TransactionRequest;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class CMClient {

    //userContext 环境变量
    public CMClient(UserContext userContext) throws IllegalAccessException, InvocationTargetException, InvalidArgumentException, InstantiationException, NoSuchMethodException, CryptoException, ClassNotFoundException {
    }


    //创建channel
    //     * @param channelName  channel的名字
    //     * @param order        order的信息
    //     * @param txPath      创建channel所需的tx文件
    public Channel createCMChannel(String channelName, Orderer order, String txPath) throws IOException, InvalidArgumentException, TransactionException{

        return null;
    }

    //安装合约底层代码
    /**
     * @param lang              合约开发语言
     * @param chaincodeName     合约名称
     * @param chaincodeVersion  合约版本
     * @param chaincodeLocation 合约的目录路径
     * @param chaincodePath     合约的文件夹
     * @param peers             安装的peers 节点
     */
    public Object installCMcode(TransactionRequest.Type lang, String chaincodeName, String chaincodeVersion, String chaincodeLocation, String chaincodePath, List<Peer> peers) throws InvalidArgumentException, ProposalException{

        return null;
    }


    public Object initCMcode(String channelName, TransactionRequest.Type lang, String chaincodeName, String chaincodeVersion, Orderer order, Peer peer, String funcName, String[] args) throws TransactionException, ProposalException, InvalidArgumentException{

        return null;
    }


}
