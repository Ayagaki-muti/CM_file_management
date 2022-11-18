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
public class DepartInfo {
    private String departId;
    private String departName;
    private String departSuperior;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DepartInfo that = (DepartInfo) o;
        return Objects.equals(departId, that.departId) &&
                Objects.equals(departName, that.departName) &&
                Objects.equals(departSuperior, that.departSuperior);
    }

    @Override
    public int hashCode() {
        return Objects.hash(departId, departName, departSuperior);
    }

    @Override
    public String toString() {
        return "{" +
                "departId=" + departId +
                ", departName=" + departName +
                ", departSuperior=" + departSuperior +
                '}';
    }
}
