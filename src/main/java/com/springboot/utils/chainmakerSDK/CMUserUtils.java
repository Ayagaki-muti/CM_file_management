package com.springboot.utils.chainmakerSDK;

import org.bouncycastle.crypto.CryptoException;
import org.hyperledger.fabric.sdk.Enrollment;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/*
    用户工具类用于读取证书和私钥信息到java对象中
 */
public class CMUserUtils {

    /**
     * @param keyFolderPath  私钥的目录
     * @param keyFileName    私钥的文件名
     * @param certFolderPath 证书的目录
     * @param certFileName   证书的文件名
     * @return enrollment 带有用户信息的对象
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CryptoException
     * @throws InvalidKeySpecException
     * @description 根据证书目录和私钥目录读取到enrollment里面。
     */
    public static Enrollment getEnrollment(String keyFolderPath, String keyFileName, String certFolderPath, String certFileName)
            throws IOException, NoSuchAlgorithmException, CryptoException, InvalidKeySpecException{

        return null;
    }





}
