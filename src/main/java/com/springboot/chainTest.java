package com.springboot;

import com.springboot.utils.chainmakerSDK.CMSDK;

public class chainTest {
    public static void main(String[] args) {
        CMSDK cmsdk = new CMSDK();
        CMSDK.installChaincode("","file/chaincode/src/userInfo","userInfo");

    }
}
