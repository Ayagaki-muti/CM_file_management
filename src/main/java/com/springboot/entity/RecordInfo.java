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

public class RecordInfo {
    private String recordId;
    private String recordAdminId;
    private String recordName;
    private String recordVersion;
    private String recordTime;
    private String fileHash;
    private String recordDescribe;

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        RecordInfo that = (RecordInfo) object;
        return Objects.equals(recordId, that.recordId) &&
                Objects.equals(recordAdminId, that.recordAdminId) &&
                Objects.equals(recordName, that.recordName) &&
                Objects.equals(recordVersion, that.recordVersion) &&
                Objects.equals(recordTime, that.recordTime) &&
                Objects.equals(fileHash, that.fileHash) &&
                Objects.equals(recordDescribe, that.recordDescribe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, recordAdminId, recordName, recordVersion, recordTime, fileHash, recordDescribe);
    }

    @Override
    public String toString() {
        return "{" +
                "recordId=" + recordId +
                ", recordAdminId=" + recordAdminId +
                ", recordName=" + recordName +
                ", recordVersion=" + recordVersion +
                ", recordTime=" + recordTime +
                ", fileHash=" + fileHash +
                ", recordDescribe=" + recordDescribe +
                '}';
    }
}
