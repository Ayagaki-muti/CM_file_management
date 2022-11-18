package com.springboot.utils.chainmakerSDK;

import java.util.Collection;

public class CMSDK {

    private String CMCodeName; //智能合约名字

    public CMSDK(String CMCodeName) {
        this.CMCodeName = CMCodeName;
    }

    //安装智能合约
    /**
     *
     * @param chaincodeVersion 合约版本
     * @param chaincodeLocation 合约位置
     * @param chaincodeName 合约名称
     * @return
     */
    public static Object installCMcode(String chaincodeVersion, String chaincodeLocation, String chaincodeName){

        return null;
    }

    //合约实例化
    /**
     *
     * @param chaincodeName 合约名字
     * @param chaincodeVersion 合约版本
     * @return
     */
    public static Object CMinstantiated(String chaincodeName, String chaincodeVersion) {

        return null;
    }

    //合约升级
    /**
     *
     * @param chaincodeName 合约名字
     * @param chaincodeVersion 合约版本
     * @return
     */
    public static Object upgradeCMcode(String chaincodeName, String chaincodeVersion) {

        return null;
    }

    //合约唤醒
    /**
     *
     * @param initArgs 传参
     * @return
     */
    public boolean CMinvoke(String[] initArgs) {

        return false;
    }


    //合约查询
    /**
     *
     * @param initArgs 传参
     * @return
     */
    public Collection queryCMcode(String[] initArgs) {

        return null;
    }

    //查询所有合约
    /**
     *
     * @param initArgs 传参
     * @return
     */
    public Collection queryAllCMcode(String[] initArgs){

        return null;
    }

}
