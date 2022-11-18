package com.springboot.utils.fabricSDK;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;

import java.io.Serializable;
import java.security.Security;
import java.util.Set;

/**
 * @description 用户类（fabric-sdk）用户对象的定义
 */
public class UserContext implements User, Serializable {//对channel里的用户进行绑定、关联

    static {//加密算法
        Security.addProvider(new BouncyCastleProvider());
    }

    private String name; //用户名称
    private Set<String> roles;//角色
    private String account; //账户
    private String affiliation;  //组织机构
    private Enrollment enrollment;//通过 Enrollment生成证书
    private String mspId;

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @Override
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    @Override
    public Enrollment getEnrollment() {
        return enrollment;
    }//签名、证书

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    @Override
    public String getMspId() {
        return mspId;
    }

    public void setMspId(String mspId) {
        this.mspId = mspId;
    }
}
