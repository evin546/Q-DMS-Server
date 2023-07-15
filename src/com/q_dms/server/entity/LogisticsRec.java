package com.q_dms.server.entity;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 物流记录类
 * 对应数据库表logistics_record
 */
public class LogisticsRec implements Serializable {
    @Serial
    private static final long serialVersionUID = -8382155492698465503L;
    //物流ID
    private int logisticsId;
    //采集时间（物流记录创建时间）
    private Timestamp creationTime;
    //目的地
    private String destination;
    //物流状态
    private String status;
    //经手人
    private String handler;
    //收货人
    private String consignee;
    //物流类型
    private String logisticsType;
    //物流记录创建者（对应登录的用户名）
    private String recCreator;

    public LogisticsRec(int logisticsId, Timestamp creationTime, String destination, String status, String handler, String consignee, String logisticsType, String recCreator) {
        this.logisticsId = logisticsId;
        this.creationTime = creationTime;
        this.destination = destination;
        this.status = status;
        this.handler = handler;
        this.consignee = consignee;
        this.logisticsType = logisticsType;
        this.recCreator = recCreator;
    }

    public LogisticsRec() {
    }

    public int getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(int logisticsId) {
        this.logisticsId = logisticsId;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(String logisticsType) {
        this.logisticsType = logisticsType;
    }

    public String getRecCreator() {
        return recCreator;
    }

    public void setRecCreator(String recCreator) {
        this.recCreator = recCreator;
    }

    @Override
    public String toString() {
        return "LogisticsRec{" +
                "logisticsId=" + logisticsId +
                ", creationTime=" + creationTime +
                ", destination='" + destination + '\'' +
                ", status='" + status + '\'' +
                ", handler='" + handler + '\'' +
                ", consignee='" + consignee + '\'' +
                ", logisticsType='" + logisticsType + '\'' +
                ", recCreator='" + recCreator + '\'' +
                '}';
    }
}

