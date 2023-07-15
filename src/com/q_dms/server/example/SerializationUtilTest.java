package com.q_dms.server.example;

import com.q_dms.server.dbhandler.DatabaseUtil;
import com.q_dms.server.entity.LogRec;
import com.q_dms.server.entity.LogisticsRec;
import com.q_dms.server.entity.User;
import com.q_dms.server.serialization.ObjectSerializationUtil;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class SerializationUtilTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException {
        User user = new User("user1", "user1", "女");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        LogRec logRec = new LogRec(-1, timestamp, "成都双流", "2", "王七", "192.168.222.2", "2", "-1");
        LogisticsRec logisticsRec = new LogisticsRec(-1, timestamp, "四川成都双流", "0", "王六", "小明", "2", "-1");

        byte[] userBytesSerialized = ObjectSerializationUtil.serialize(user);
        User user1 = (User) ObjectSerializationUtil.deserialize(userBytesSerialized);
        System.out.println(user1.getUsername());

        byte[] logRecByteSerialized = ObjectSerializationUtil.serialize(logRec);
        LogRec logRec1 = (LogRec) ObjectSerializationUtil.deserialize(logRecByteSerialized);
        System.out.println(logRec1.getLogId());

        byte[] logisticsRecBytesSerialized = ObjectSerializationUtil.serialize(logisticsRec);
        LogisticsRec logisticsRec1 = (LogisticsRec) ObjectSerializationUtil.deserialize(logisticsRecBytesSerialized);
        System.out.println(logisticsRec1.getConsignee());

        DatabaseUtil databaseUtil = DatabaseUtil.getDatabaseUtil();
        //System.out.println(databaseUtil.addLogRec(user1,logRec1));

        //System.out.println(databaseUtil.addLogisticsRec(user1,logisticsRec1));

        List<LogRec> logRecList = databaseUtil.selectAllLogRec(user);
        byte[] logRecListSerialized = ObjectSerializationUtil.serialize(logRecList);

        //该警告可用在方法前用注解@SuppressWarnings("unchecked")消去
        List<LogRec> logRecList1 = (List<LogRec>) ObjectSerializationUtil.deserialize(logRecListSerialized);
        System.out.println(logisticsRec1);


    }
}
