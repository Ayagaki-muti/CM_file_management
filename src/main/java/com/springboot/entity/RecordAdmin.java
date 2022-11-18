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
//档案管理员
public class RecordAdmin {
    private String recordAdminId;
    private String recordAdminPassword;
    private String recordAdminName;
    private String departId;
    private String recordAdminLevel;
    private String recordAdminPhone;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecordAdmin that = (RecordAdmin) o;
        return Objects.equals(recordAdminId, that.recordAdminId) &&
                Objects.equals(recordAdminPassword, that.recordAdminPassword) &&
                Objects.equals(recordAdminName, that.recordAdminName) &&
                Objects.equals(departId, that.departId) &&
                Objects.equals(recordAdminLevel, that.recordAdminLevel) &&
                Objects.equals(recordAdminPhone, that.recordAdminPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordAdminId, recordAdminPassword, recordAdminName, departId, recordAdminLevel, recordAdminPhone);
    }

    @Override
    public String toString() {
        return "{" +
                "recordAdminId=" + recordAdminId +
                ", recordAdminPassword=" + recordAdminPassword +
                ", recordAdminName=" + recordAdminName +
                ", departId=" + departId +
                ", recordAdminLevel=" + recordAdminLevel +
                ", recordAdminPhone=" + recordAdminPhone +
                '}';
    }
}
