package com.springboot.utils.chainmakerSDK;

import java.util.Collection;

public class CMSDK {

    private String CMCodeName; //智能合约名字

    public CMSDK(String CMCodeName) {
        this.CMCodeName = CMCodeName;
    }

    //安装智能合约
    public static Object installCMcode(String chaincodeVersion, String chaincodeLocation, String chaincodeName){

        return null;
    }

    //合约实例化
    public static Object CMinstantiated(String chaincodeName, String chaincodeVersion) {

        return null;
    }

    //合约升级
    public static Object upgradeCMcode(String chaincodeName, String chaincodeVersion) {

        return null;
    }

    //合约唤醒
    public boolean CMinvoke(String[] initArgs) {

        return false;
    }


    //合约查询
    public Collection queryCMcode(String[] initArgs) {

        return null;
    }

    //查询所有合约
    public Collection queryAllCMcode(String[] initArgs){

        return null;
    }

}
