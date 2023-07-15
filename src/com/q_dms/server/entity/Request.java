package com.q_dms.server.entity;

import java.io.Serial;
import java.io.Serializable;

/**
 * 供前后端进行交互的“包装类”
 */
public class Request implements Serializable {

    @Serial
    private static final long serialVersionUID = -2245320065709129630L;
    //User类的对象
    User user;
    //请求业务id
    int businessId;

    LogRec logRec;

    LogisticsRec logisticsRec;

    public Request(User user, int businessId, LogRec logRec, LogisticsRec logisticsRec) {
        this.user = user;
        this.businessId = businessId;
        this.logRec = logRec;
        this.logisticsRec = logisticsRec;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getBusinessId() {
        return businessId;
    }

    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    public LogRec getLogRec() {
        return logRec;
    }

    public void setLogRec(LogRec logRec) {
        this.logRec = logRec;
    }

    public LogisticsRec getLogisticsRec() {
        return logisticsRec;
    }

    public void setLogisticsRec(LogisticsRec logisticsRec) {
        this.logisticsRec = logisticsRec;
    }
}
