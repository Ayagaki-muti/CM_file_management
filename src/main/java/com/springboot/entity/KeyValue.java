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
public class KeyValue {
    /**
     * key为合约的名字 例如userInfo
     */
    private String key;
    private String valueA;//超级管理员id
    private String valueB;//超级管理员

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        KeyValue keyValue = (KeyValue) o;
        return Objects.equals(key, keyValue.key) &&
                Objects.equals(valueA, keyValue.valueA) &&
                Objects.equals(valueB, keyValue.valueB);
    }

    @Override//计算哈希值
    public int hashCode() {
        return Objects.hash(key, valueA, valueB);
    }

    @Override
    public String toString() {
        return "{" +
                "key=" + key +
                ", valueA=" + valueA +
                ", valueB=" + valueB +
                '}';
    }
}
