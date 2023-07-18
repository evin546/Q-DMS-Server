package com.q_dms.server.example;

import com.q_dms.server.dbhandler.DatabaseUtil;
import com.q_dms.server.entity.LogRec;
import com.q_dms.server.entity.LogisticsRec;
import com.q_dms.server.entity.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * 数据库表log_record的查找
 */
public class DatabaseUtilTest {

    public static void main(String[] args) throws SQLException {
        DatabaseUtil databaseUtil = DatabaseUtil.getDatabaseUtil();
        User user = new User("user1", "user1", "男");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        LogRec logRec = new LogRec(62, timestamp, "四川", "11111", "十七", "192.168.2.2", "3", "-1");
        LogRec logRec1 = new LogRec(-1, timestamp, "成都", " ", " ", " ", " ", "user1");
        LogisticsRec logisticsRec = new LogisticsRec(33, timestamp, "四川成都", "01111", "小五", "小六", "1", "-1");
        LogisticsRec logisticsRec1 = new LogisticsRec(-1, null, "北", "", "五", "", "", "user1");

//        System.out.println(databaseUtil.loginCheck(user));
//        System.out.println(databaseUtil.registerUser(user));
//        System.out.println(databaseUtil.selectAllLogRec(user));
//        System.out.println(databaseUtil.selectAllLogisticsRec(user));
//        System.out.println(logRec);
//        System.out.println(databaseUtil.addLogRec(user,logRec));
//        System.out.println(databaseUtil.addLogisticsRec(user, logisticsRec));
        //System.out.println(databaseUtil.changePassword(user));
        //System.out.println(databaseUtil.deleteLogisticsRec(logisticsRec));
        //System.out.println(databaseUtil.searchLogRecInDatabase(logRec1));
        //System.out.println(databaseUtil.searchLogisticsRecInDatabase(logisticsRec1));


    }

}




