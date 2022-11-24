package com.springboot.utils.chainmakerSDK;

import java.util.Collection;
import java.util.Map;

public class CMSDK {

    private String chainCodeName; //智能合约名字

    public CMSDK(String chainCodeName) {
        this.chainCodeName = chainCodeName;
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
