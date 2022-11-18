package com.springboot.utils.chainmakerSDK;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.chainmaker.pb.common.ContractOuterClass;
import org.chainmaker.sdk.ChainClient;
import org.chainmaker.sdk.Node;
import org.chainmaker.sdk.User;
public class CMClient {

    /**
     * 构造函数
     * @param user
     * @return
     */
    public CMClient(User user){


    }

    /**
     * 创建channel
     * @param channelName channel的名字
     * @param node 节点
     * @param txPath 创建channel所需的tx文件
     * @return
     */
//    public ChainClient createCMChannel(String channelName, Node node, String txPath) throws IOException, InvalidArgumentException, TransactionException{
//
//        return null;
//    }

    /**
     * 安装合约底层代码
     * @param lang              合约开发语言
     * @param chaincodeName     合约名称
     * @param chaincodeVersion  合约版本
     * @param chaincodeLocation 合约的目录路径
     * @param chaincodePath     合约的文件夹
     * @param nodeList          安装的节点
     * @return
     */
    public Object installCMcode(ContractOuterClass.RuntimeType lang, String chaincodeName, String chaincodeVersion,
                                String chaincodeLocation, String chaincodePath, List<Node> nodeList){

        return null;
    }

    /**
     * 初始化合约底层代码
     * @param channelName channel名字
     * @param lang  合约开发语言
     * @param chaincodeName 合约名称
     * @param chaincodeVersion 合约版本
     * @param node node的信息
     * @param funcName 合约实例化执行的函数
     * @param args 合约实例化执行的参数
     * @return
     */
    public Object initCMcode(String channelName, ContractOuterClass.RuntimeType lang, String chaincodeName, String chaincodeVersion,
                             Node node, String funcName, String[] args) {

        return null;
    }

    /**
     * 合约的升级底层代码
     * @param channelName channel名字
     * @param lang 合约开发语言
     * @param chaincodeName 合约名称
     * @param chaincodeVersion 合约版本
     * @param node 节点
     * @param funcName 合约实例化执行的函数
     * @param args  合约实例化执行的参数
     * @return
     */
    public Object upgradeCMcode(String channelName, ContractOuterClass.RuntimeType lang, String chaincodeName,
                                String chaincodeVersion, String funcName, Node node, String[] args) {

        return null;
    }

    /**
     * 执行合约底层代码
     * @param channelName channel名字
     * @param lang 合约开发语言
     * @param chaincodeName 合约名称
     * @param node 节点
     * @param nodes
     * @param funcName 合约调用执行的函数名称
     * @param args 合约调用执行的参数
     * @return
     */
    public Object invokeCMcode(String channelName, ContractOuterClass.RuntimeType lang,
                               String chaincodeName, Node node, List<Node> nodes, String funcName, String[] args){

        return null;
    }

    /**
     * 合约的查询底层代码
     * @param node node信息
     * @param channelName channel名字
     * @param lang  合约开发语言
     * @param chaincodeName 合约名称
     * @param funcName 合约查询执行的函数名称
     * @param args 合约查询执行的参数
     * @return
     */
    public Map queryCMcode(List<Node> node, String channelName, ContractOuterClass.RuntimeType lang,
                           String chaincodeName, String funcName, String[] args) {

        return null;
    }

    /**
     * 获取order节点信息
     * @param name
     * @param grpcUrl
     * @param tlsFilePath
     * @return
     */
    public Node getCMOrder(String name, String grpcUrl, String tlsFilePath){

        return null;
    }

    /**
     * 获取peer节点
     * @param name
     * @param grpcUrl
     * @param tlsFilePath
     * @return
     */
    public Node getCMNode(String name, String grpcUrl, String tlsFilePath){

        return null;
    }


    /**
     * 获取已有的channel
     * @param channelName
     * @return
     */
    public ChainClient getChainClient(String channelName){

        return null;
    }



}
