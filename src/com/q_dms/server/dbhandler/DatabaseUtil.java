package com.q_dms.server.dbhandler;

import com.q_dms.server.entity.LogRec;
import com.q_dms.server.entity.LogisticsRec;
import com.q_dms.server.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库操作工具类
 * 单例模式：外部不可实例化，仅能调用getDatabaseUtil()方法获得实例
 */
public class DatabaseUtil {

    private static final DatabaseUtil databaseUtil = new DatabaseUtil();
    //mySQL 连接信息
    String db_url = "jdbc:mysql://127.0.0.1:3306/q_dms_db";
    String db_username = "root";
    String db_password = "root";


    //连接对象
    Connection conn;
    //执行SQL的对象
    PreparedStatement pstmt;
    //SQL执行结果
    ResultSet rs;

    private DatabaseUtil() {
    }

    /**
     * 方法：获取本类唯一实例
     *
     * @return DatabaseUtil 类型的对象
     */
    public static DatabaseUtil getDatabaseUtil() {
        return databaseUtil;
    }

    /**
     * 内部方法：获得sql语句执行对象pstmt
     *
     * @param sql 需要执行的SQL语句
     * @return ResultSet（正常）/null（异常）
     */
    private PreparedStatement getPstmt(String sql) throws SQLException {
        conn = DriverManager.getConnection(db_url, db_username, db_password);
        pstmt = conn.prepareStatement(sql);
        return pstmt;
    }

    /**
     * 关闭数据库连接，释放资源
     *
     * @throws SQLException 关闭错误
     */
    private void closeConnection() throws SQLException {
        rs.close();
        pstmt.close();
        conn.close();
    }

    /**
     * 用户注册逻辑(业务ID：1)
     *
     * @param user User类的对象
     * @return 0（注册成功）/ 1（注册失败，已存在相同的用户名）/ 2（失败，未知原因）
     */
    public int registerUser(User user) {
        //执行注册用户名校验，判断注册用户名是否可用
        try {
            String sql = "SELECT * FROM `user_info` WHERE `username` = ?";
            pstmt = this.getPstmt(sql);
            pstmt.setString(1, user.getUsername());
            rs = pstmt.executeQuery();
            //ResultSet的isBeforeFirst()方法，若ResultSet为非空则返回ture
            boolean queryResult = rs.isBeforeFirst();
            this.closeConnection();
            if (queryResult) {
                //查询结果为ture，则证明用户名不可用，注册失败，返回 1
                return 1;
            } else {
                //查询结果为false，用户名可用，执行后续注册逻辑
                String sql1 = "INSERT INTO `user_info` VALUES(?,?,?);";
                pstmt = this.getPstmt(sql1);
                pstmt.setString(1, user.getUsername());
                pstmt.setString(2, user.getPassword());
                pstmt.setString(3, user.getSex());
                //方法返回受影响的行数，若行数大于0，则注册数据写入成功，注册成功
                int count = pstmt.executeUpdate();
                //关闭连接，释放资源
                this.closeConnection();
                if (count > 0) {
                    return 0; //注册成功
                } else {
                    return 2; //注册失败，数据库修改失败
                }
            }
        } catch (SQLException e) {
            return 2; //注册失败，数据库修改失败
        }
    }

    /**
     * 登录时账号验证逻辑（业务ID：2）
     *
     * @param user User类的对象
     * @return 0（登录成功）/ 1（登录失败：数据库找不到匹配的用户名密码记录，登录失败）
     * @throws SQLException 异常
     */
    public int loginCheck(User user) throws SQLException {
        //判断用户名密码是否正确
        String sql = "SELECT `username` FROM `user_info` WHERE `username` = ? AND `password` = ?";
        pstmt = this.getPstmt(sql);
        pstmt.setString(1, user.getUsername());
        pstmt.setString(2, user.getPassword());
        rs = pstmt.executeQuery();

        if (rs.next()) {
            this.closeConnection();
            return 0;
        } else {
            this.closeConnection();
            return 1;
        }
    }

    /**
     * 查询账号名下的所有日志记录（业务ID：3）
     * * @param username 当前登录的用户名
     *
     * @return List<LogRec>类型的对象（正常）/null（异常：查询不到）
     */
    public List<LogRec> selectAllLogRec(User user) {
        List<LogRec> logRecList = new ArrayList<>();
        try {
            //执行sql语句
            String sql = "SELECT * FROM `log_record` WHERE `recCreator` = ?";
            pstmt = this.getPstmt(sql);
            pstmt.setString(1, user.getUsername());
            rs = pstmt.executeQuery();
            //rs为空，返回null
            if (!rs.next()) {
                return null;
            }
            do {
                int logId = rs.getInt("logId");
                Timestamp creationTime = rs.getTimestamp("creationTime");
                String creationLocation = rs.getString("creationLocation");
                String status = rs.getString("status");
                String logUsername = rs.getString("logUsername");
                String ip = rs.getString("ip");
                String logType = rs.getString("logType");
                String recCreator = rs.getString("recCreator");

                LogRec logRec = new LogRec();
                logRec.setLogId(logId);
                logRec.setCreationTime(creationTime);
                logRec.setCreationLocation(creationLocation);
                logRec.setStatus(status);
                logRec.setLogUsername(logUsername);
                logRec.setIp(ip);
                logRec.setLogType(logType);
                logRec.setRecCreator(recCreator);
                logRecList.add(logRec);
            }while(rs.next());

            this.closeConnection();
        } catch (SQLException e) {
            return null;
        }
        return logRecList;
    }

    /**
     * 查询账号名下的所有物流记录（业务ID：4）
     *
     * @param user 类的对象
     * @return List<LogisticsRec>类型的对象（正常）/null（异常：查询不到）
     */
    public List<LogisticsRec> selectAllLogisticsRec(User user) {
        List<LogisticsRec> logisticsRecList = new ArrayList<>();
        try {
            //执行sql语句
            String sql = "SELECT * FROM `logistics_record` WHERE `recCreator` = ?;";
            pstmt = this.getPstmt(sql);
            pstmt.setString(1, user.getUsername());
            rs = pstmt.executeQuery();

            if (!rs.next()) {
                return null;
            }
            do {
                int logisticsId = rs.getInt("logisticsId");
                Timestamp creationTime = rs.getTimestamp("creationTime");
                String destination = rs.getString("destination");
                String status = rs.getString("status");
                String handler = rs.getString("handler");
                String consignee = rs.getString("consignee");
                String logisticsType = rs.getString("logisticsType");
                String recCreator = rs.getString("recCreator");

                LogisticsRec logisticsRec = new LogisticsRec();
                logisticsRec.setLogisticsId(logisticsId);
                logisticsRec.setCreationTime(creationTime);
                logisticsRec.setDestination(destination);
                logisticsRec.setStatus(status);
                logisticsRec.setHandler(handler);
                logisticsRec.setConsignee(consignee);
                logisticsRec.setLogisticsType(logisticsType);
                logisticsRec.setRecCreator(recCreator);
                logisticsRecList.add(logisticsRec);
            }while(rs.next());
            this.closeConnection();
        } catch (SQLException e) {
            return null;
        }
        return logisticsRecList;
    }

    /**
     * 添加日志记录 （业务ID：5）
     *
     * @param user   User类的对象（标识当前操作用户）
     * @param logRec LogRec的对象
     * @return 0（添加成功）/ 1（添加失败）
     */
    public int addLogRec(User user, LogRec logRec) {
        try {
            String sql = "INSERT INTO `log_record`(`creationTime`,`creationLocation`,`status`,`logUsername`,`ip`,`logType`,`recCreator`) VALUES (?, ?, ?, ?, ?, ?, ?);";
            pstmt = this.getPstmt(sql);
            pstmt.setTimestamp(1, logRec.getCreationTime());
            pstmt.setString(2, logRec.getCreationLocation());
            pstmt.setString(3, logRec.getStatus());
            pstmt.setString(4, logRec.getLogUsername());
            pstmt.setString(5, logRec.getIp());
            pstmt.setString(6, logRec.getLogType());
            pstmt.setString(7, user.getUsername());
            int count = pstmt.executeUpdate();
            if (count > 0) {
                return 0;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            return 1;
        }
    }

    /**
     * 添加物流记录 （业务ID：6）
     *
     * @param user         User类的对象（标识当前操作用户）
     * @param logisticsRec LogisticsRec类的对象
     * @return 0（添加成功）/ 1（添加失败）
     */
    public int addLogisticsRec(User user, LogisticsRec logisticsRec) {
        try {
            String sql = "INSERT INTO `logistics_record`(`creationTime`,`destination`,`status`,`handler`,`consignee`, `logisticsType`,`recCreator`) VALUES (?, ?, ?, ?, ?, ?, ?);";
            pstmt = this.getPstmt(sql);
            pstmt.setTimestamp(1, logisticsRec.getCreationTime());
            pstmt.setString(2, logisticsRec.getDestination());
            pstmt.setString(3, logisticsRec.getStatus());
            pstmt.setString(4, logisticsRec.getHandler());
            pstmt.setString(5, logisticsRec.getConsignee());
            pstmt.setString(6, logisticsRec.getLogisticsType());
            pstmt.setString(7, user.getUsername());
            int count = pstmt.executeUpdate();
            if (count > 0) {
                return 0;
            } else {
                return 1;
            }
        } catch (SQLException e) {
            return 1;
        }
    }


}
