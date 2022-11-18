package com.springboot.utils.chainmakerSDK;


import org.hyperledger.fabric.sdk.Enrollment;

import java.util.Set;

/*
    用户类 用户对象的定义
 */
public class CMUser {

    private String name; //用户名称
    private Set<String> roles;//角色
    private String account; //账户
    private String affiliation;  //组织机构
    private Enrollment enrollment;//通过 Enrollment生成证书
    private String mspId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public Enrollment getEnrollment() {
        return enrollment;
    }

    public void setEnrollment(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    public String getMspId() {
        return mspId;
    }

    public void setMspId(String mspId) {
        this.mspId = mspId;
    }
}
