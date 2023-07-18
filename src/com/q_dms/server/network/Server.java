package com.q_dms.server.network;

import com.q_dms.server.dbhandler.DatabaseUtil;
import com.q_dms.server.entity.LogRec;
import com.q_dms.server.entity.LogisticsRec;
import com.q_dms.server.entity.Request;
import com.q_dms.server.entity.User;
import com.q_dms.server.serialization.ObjectSerializationUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.sql.Timestamp;

import static com.q_dms.server.serialization.ObjectSerializationUtil.serialize;

public class Server {

    static final int REGISTER = 1;
    static final int LOGIN = 2;
    static final int GET_ALL_LOG_REC = 3;
    static final int GET_ALL_LOGISTICS_REC = 4;
    static final int ADD_LOG_REC = 5;
    static final int ADD_LOGISTICS_REC = 6;
    static final int CHANG_PASSWORD = 7;
    static final int DELETE_LOG_REC = 8;
    static final int DELETE_LOGISTICS_REC = 9;
    static final int SEARCH_LOG_REC = 10;
    static final int SEARCH_LOGISTICS_REC = 11;


    /**
     * 静态方法：处理客户端传输的request对象，调用相应业务函数并反馈
     * 注意！服务端每处理一次客户端请求后会主动与客户端断开连接，客户端进行新请求时需要重新建立
     *
     * @throws IOException            .
     * @throws ClassNotFoundException .
     * @throws SQLException           .
     */
    public static void clientRequestHandler() throws IOException, ClassNotFoundException, SQLException {
        // 创建ServerSocket对象，并指定监听的端口号
        ServerSocket serverSocket = new ServerSocket(8888);
        System.out.println(getCurrentTime() +" 服务端初始化成功，当前监听端口：" + serverSocket.getLocalPort());
        //死循环接收客户端数据
        while (true) {
            // 接收客户端连接
            Socket clientSocket = serverSocket.accept();

            // 创建输入流和输出流
            InputStream inputStream = clientSocket.getInputStream();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[4096];

            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }

            byte[] byteArray = outputStream.toByteArray();
            // 反序列化客户端传来的request对象
            Request request = (Request) ObjectSerializationUtil.deserialize(byteArray);
            int businessId = request.getBusinessId();
            User user = request.getUser();
            LogRec logRec = request.getLogRec();
            LogisticsRec logisticsRec = request.getLogisticsRec();
            System.out.println( getCurrentTime() + " 客户端业务ID： " + businessId);


            //执行对应业务逻辑
            switch (businessId) {
                case REGISTER ->
                        sendResponse(clientSocket, DatabaseUtil.getDatabaseUtil().registerUser(user));
                case LOGIN ->
                        sendResponse(clientSocket, DatabaseUtil.getDatabaseUtil().loginCheck(user));
                case GET_ALL_LOG_REC ->
                        sendResponse(clientSocket, DatabaseUtil.getDatabaseUtil().selectAllLogRec(user));
                case GET_ALL_LOGISTICS_REC ->
                        sendResponse(clientSocket, DatabaseUtil.getDatabaseUtil().selectAllLogisticsRec(user));
                case ADD_LOG_REC ->
                    sendResponse(clientSocket, DatabaseUtil.getDatabaseUtil().addLogRec(user, logRec));
                case ADD_LOGISTICS_REC ->
                        sendResponse(clientSocket, DatabaseUtil.getDatabaseUtil().addLogisticsRec(user, logisticsRec));
                case CHANG_PASSWORD ->
                        sendResponse(clientSocket, DatabaseUtil.getDatabaseUtil().changePassword(user));
                case DELETE_LOG_REC ->
                        sendResponse(clientSocket, DatabaseUtil.getDatabaseUtil().deleteLogRec(logRec));
                case DELETE_LOGISTICS_REC ->
                        sendResponse(clientSocket, DatabaseUtil.getDatabaseUtil().deleteLogisticsRec(logisticsRec));
                case SEARCH_LOG_REC ->
                    sendResponse(clientSocket, DatabaseUtil.getDatabaseUtil().searchLogRecInDatabase(logRec));
                case SEARCH_LOGISTICS_REC ->
                        sendResponse(clientSocket, DatabaseUtil.getDatabaseUtil().searchLogisticsRecInDatabase(logisticsRec));
                default ->
                        System.out.println(getCurrentTime() + " 客户端业务ID提交错误！");
            }
            clientSocket.close();
        }
    }

    //服务端响应数据：
    // 登录/注册 ———— 0成功，1失败
    // 查询账号名下（日志/物流）记录，List<LogRec>/List<LogisticsRec>成功，null失败
    // 添加（日志/物流）记录，0成功，1失败

    /**
     * 静态方法：给客户端发送响应数据
     * @param clientSocket socket类对象
     * @param response     发送的响应数据
     * @throws IOException .
     */
    public static void sendResponse(Socket clientSocket, Object response) throws IOException {
        byte[] serializedResponse = serialize(response);
        System.out.println(getCurrentTime() +" 向客户端发送响应：" + response);
        OutputStream outputStream = clientSocket.getOutputStream();
        // 发送响应数据到客户端
        outputStream.write(serializedResponse);
        outputStream.flush();
    }

    /**
     * 获取当前时间
     * @return Timestamp 类型的对象，表示当前时间
     */
    static Timestamp getCurrentTime(){
        return new Timestamp(System.currentTimeMillis());
    }

}







