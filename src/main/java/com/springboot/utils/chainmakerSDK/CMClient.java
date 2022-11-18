package com.springboot.utils.chainmakerSDK;

import com.springboot.utils.fabricSDK.UserContext;
import org.hyperledger.fabric.sdk.Channel;
import org.hyperledger.fabric.sdk.Orderer;
import org.hyperledger.fabric.sdk.Peer;
import org.hyperledger.fabric.sdk.TransactionRequest;
import org.hyperledger.fabric.sdk.exception.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class CMClient {

    /**
     * 构造函数
     * @param userContext
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InvalidArgumentException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws CryptoException
     * @throws ClassNotFoundException
     */
    public CMClient(UserContext userContext) throws IllegalAccessException, InvocationTargetException, InvalidArgumentException, InstantiationException, NoSuchMethodException, CryptoException, ClassNotFoundException {
    }



    /**
     * 创建channel
     * @param channelName channel的名字
     * @param order order的信息
     * @param txPath 创建channel所需的tx文件
     * @return
     * @throws IOException
     * @throws InvalidArgumentException
     * @throws TransactionException
     */
    public Channel createCMChannel(String channelName, Orderer order, String txPath) throws IOException, InvalidArgumentException, TransactionException{

        return null;
    }


    /**
     * 安装合约底层代码
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


    /**
     * 初始化合约底层代码
     * @param channelName channel名字
     * @param lang  合约开发语言
     * @param chaincodeName 合约名称
     * @param chaincodeVersion 合约版本
     * @param order order的信息
     * @param peer  peer信息
     * @param funcName 合约实例化执行的函数
     * @param args 合约实例化执行的参数
     * @return
     * @throws TransactionException
     * @throws ProposalException
     * @throws InvalidArgumentException
     */
    public Object initCMcode(String channelName, TransactionRequest.Type lang, String chaincodeName, String chaincodeVersion, Orderer order, Peer peer, String funcName, String[] args) throws TransactionException, ProposalException, InvalidArgumentException{

        return null;
    }


    /**
     * @param channelName channel名字
     * @param lang 合约开发语言
     * @param chaincodeName 合约名称
     * @param chaincodeVersion 合约版本
     * @param order order的信息
     * @param peer peer信息
     * @param funcName 合约实例化执行的函数
     * @param args  合约实例化执行的参数
     * @throws TransactionException
     * @throws ProposalException
     * @throws InvalidArgumentException
     * @throws IOException
     * @throws ChaincodeEndorsementPolicyParseException
     * @description 合约的升级底层代码
     */
    public Object upgradeCMcode(String channelName, TransactionRequest.Type lang, String chaincodeName, String chaincodeVersion, Orderer order, Peer peer, String funcName, String[] args) throws TransactionException, ProposalException, InvalidArgumentException, IOException, ChaincodeEndorsementPolicyParseException{

        return null;
    }



    /**
     * 执行合约底层代码
     * @param channelName channel名字
     * @param lang 合约开发语言
     * @param chaincodeName 合约名称
     * @param order order的信息
     * @param peers peer信息
     * @param funcName 合约调用执行的函数名称
     * @param args 合约调用执行的参数
     * @return
     * @throws TransactionException
     * @throws ProposalException
     * @throws InvalidArgumentException
     */
    public Object invokeCMcode(String channelName, TransactionRequest.Type lang, String chaincodeName, Orderer order, List<Peer> peers, String funcName, String[] args) throws TransactionException, ProposalException, InvalidArgumentException{

        return null;
    }




    /**
     * 合约的查询底层代码
     * @param peers peer信息
     * @param channelName channel名字
     * @param lang  合约开发语言
     * @param chaincodeName 合约名称
     * @param funcName 合约查询执行的函数名称
     * @param args 合约查询执行的参数
     * @return
     * @throws TransactionException
     * @throws InvalidArgumentException
     * @throws ProposalException
     */
    public Map queryCMcode(List<Peer> peers, String channelName, TransactionRequest.Type lang, String chaincodeName, String funcName, String[] args) throws TransactionException, InvalidArgumentException, ProposalException{

        return null;
    }





    /**
     * 获取order节点信息
     * @param name
     * @param grpcUrl
     * @param tlsFilePath
     * @return
     * @throws InvalidArgumentException
     */
    public Orderer getCMOrder(String name, String grpcUrl, String tlsFilePath) throws InvalidArgumentException{

        return null;
    }





    /**
     * 获取peer节点
     * @param name
     * @param grpcUrl
     * @param tlsFilePath
     * @return
     * @throws InvalidArgumentException
     */
    public Peer getCMPeer(String name, String grpcUrl, String tlsFilePath) throws InvalidArgumentException{

        return null;
    }



    //获取已有的channel
    /**
     *
     * @param channelName
     * @return
     * @throws InvalidArgumentException
     * @throws TransactionException
     * @throws ProposalException
     */
    public Channel getChannel(String channelName) throws InvalidArgumentException, TransactionException, ProposalException{

        return null;
    }



}
