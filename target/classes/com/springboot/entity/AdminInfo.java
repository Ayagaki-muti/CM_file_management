package com.springboot.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor                 //无参构造
@AllArgsConstructor                //有参构造
//超级管理员
public class AdminInfo {
    private String adminId;
    private String adminPassword;
    private String adminName;
    private String adminSex;
    private String adminAddress;
    private String adminPhone;
    private String adminLevel;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AdminInfo adminInfo = (AdminInfo) o;
        return Objects.equals(adminId, adminInfo.adminId) &&
                Objects.equals(adminPassword, adminInfo.adminPassword) &&
                Objects.equals(adminName, adminInfo.adminName) &&
                Objects.equals(adminSex, adminInfo.adminSex) &&
                Objects.equals(adminAddress, adminInfo.adminAddress) &&
                Objects.equals(adminPhone, adminInfo.adminPhone) &&
                Objects.equals(adminLevel, adminInfo.adminLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adminId, adminPassword, adminName, adminSex, adminAddress, adminPhone, adminLevel);
    }

    @Override
    public String toString() {
        return "{" +
                "adminId=" + adminId +
                ", adminPassword=" + adminPassword +
                ", adminName=" + adminName +
                ", adminSex=" + adminSex +
                ", adminAddress=" + adminAddress +
                ", adminPhone=" + adminPhone +
                ", adminLevel=" + adminLevel +
                '}';
    }
}
