package com.q_dms.server.entity;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 日记记录类
 * 对应数据库表log_record
 */
public class LogRec implements Serializable {
    @Serial
    private static final long serialVersionUID = 5348612597654671098L;
    //日志ID
    private int logId;
    //采集时间（日志创建时间）
    private Timestamp creationTime;
    //采集地点
    private String creationLocation;
    //状态
    private String status;
    //日志记录时的用户名（非登录用户名）
    private String logUsername;
    //ip
    private String ip;
    //日志类型
    private String logType;
    //日志创建者（对应登录的用户名）
    private String recCreator;

    public LogRec(int logId, Timestamp creationTime, String creationLocation, String status, String logUsername, String ip, String logType, String recCreator) {
        this.logId = logId;
        this.creationTime = creationTime;
        this.creationLocation = creationLocation;
        this.status = status;
        this.logUsername = logUsername;
        this.ip = ip;
        this.logType = logType;
        this.recCreator = recCreator;
    }

    public LogRec() {
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreationLocation() {
        return creationLocation;
    }

    public void setCreationLocation(String creationLocation) {
        this.creationLocation = creationLocation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogUsername() {
        return logUsername;
    }

    public void setLogUsername(String logUsername) {
        this.logUsername = logUsername;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getRecCreator() {
        return recCreator;
    }

    public void setRecCreator(String recCreator) {
        this.recCreator = recCreator;
    }

    @Override
    public String toString() {
        return "LogRec{" +
                "logId=" + logId +
                ", creationTime=" + creationTime +
                ", creationLocation='" + creationLocation + '\'' +
                ", status='" + status + '\'' +
                ", logUsername='" + logUsername + '\'' +
                ", ip='" + ip + '\'' +
                ", logType='" + logType + '\'' +
                ", recCreator='" + recCreator + '\'' +
                '}';
    }
}


