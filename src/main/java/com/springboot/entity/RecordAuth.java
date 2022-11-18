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

public class RecordAuth {
    private String recordAuthId;
    private String recordAdminId;
    private String recordUserId;
    private String recordId;
    private String recordAuthTime;
    private String recordAuthCutOffTime;
    private String recordAuthTips;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RecordAuth that = (RecordAuth) o;
        return Objects.equals(recordAuthId, that.recordAuthId) &&
                Objects.equals(recordAdminId, that.recordAdminId) &&
                Objects.equals(recordUserId, that.recordUserId) &&
                Objects.equals(recordId, that.recordId) &&
                Objects.equals(recordAuthTime, that.recordAuthTime) &&
                Objects.equals(recordAuthCutOffTime, that.recordAuthCutOffTime) &&
                Objects.equals(recordAuthTips, that.recordAuthTips);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordAuthId, recordAdminId, recordUserId, recordId, recordAuthTime, recordAuthCutOffTime, recordAuthTips);
    }

    @Override
    public String toString() {
        return "{" +
                "recordAuthId=" + recordAuthId +
                ", recordAdminId=" + recordAdminId +
                ", recordUserId=" + recordUserId +
                ", recordId=" + recordId +
                ", recordAuthTime=" + recordAuthTime +
                ", recordAuthCutOffTime=" + recordAuthCutOffTime +
                ", recordAuthTips=" + recordAuthTips +
                '}';
    }
}
