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
/**
 * @version 1.0.0
 */
public class UserInfo {
    private String userId;
    private String userPassword;
    private String userName;
    private String userSex;
    private String userAddress;
    private String userPhone;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(userId, userInfo.userId) &&
                Objects.equals(userPassword, userInfo.userPassword) &&
                Objects.equals(userName, userInfo.userName) &&
                Objects.equals(userSex, userInfo.userSex) &&
                Objects.equals(userAddress, userInfo.userAddress) &&
                Objects.equals(userPhone, userInfo.userPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userPassword, userName, userSex, userAddress, userPhone);
    }

    @Override
    public String toString() {
        return "{" +
                "userId=" + userId +
                ", userPassword=" + userPassword +
                ", userName=" + userName +
                ", userSex=" + userSex +
                ", userAddress=" + userAddress +
                ", userPhone=" + userPhone +
                '}';
    }
}
