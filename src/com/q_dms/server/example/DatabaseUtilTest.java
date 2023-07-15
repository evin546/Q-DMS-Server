package com.q_dms.server.example;

import com.q_dms.server.dbhandler.DatabaseUtil;
import com.q_dms.server.entity.LogRec;
import com.q_dms.server.entity.LogisticsRec;
import com.q_dms.server.entity.User;

import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * 数据库表log_record的查找
 */
public class DatabaseUtilTest {

    public static void main(String[] args) throws SQLException {
        DatabaseUtil databaseUtil = DatabaseUtil.getDatabaseUtil();
        User user = new User("user9", "user1", "男");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        LogRec logRec = new LogRec(-1, timestamp, "四川", "1", "王五", "192.168.2.2", "3", "-1");
        LogisticsRec logisticsRec = new LogisticsRec(-1, timestamp, "四川成都", "0", "小五", "小六", "1", "-1");
//        System.out.println(databaseUtil.loginCheck(user));
//        System.out.println(databaseUtil.registerUser(user));
        System.out.println(databaseUtil.selectAllLogRec(user));
        System.out.println(databaseUtil.selectAllLogisticsRec(user));
//        System.out.println(databaseUtil.addLogRec(user,logRec));
//        System.out.println(databaseUtil.addLogisticsRec(user, logisticsRec));


    }
}




