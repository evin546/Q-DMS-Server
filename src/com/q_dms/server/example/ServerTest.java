package com.q_dms.server.example;

import com.q_dms.server.entity.LogRec;
import com.q_dms.server.entity.LogisticsRec;
import com.q_dms.server.entity.Request;
import com.q_dms.server.entity.User;
import com.q_dms.server.serialization.ObjectSerializationUtil;

import java.io.*;
import java.net.Socket;
import java.sql.Timestamp;

public class ServerTest {


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        User user = new User("user001", "user11213", "男");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        LogisticsRec logisticsRec = new LogisticsRec(32, timestamp, "中国", "01111", "小五", "小六", "1", "-1");
        LogisticsRec logisticsRec1 = new LogisticsRec(-1, null, "纽", "", "", "", "", "user3");
        LogRec logRec = new LogRec(-1, timestamp, "", "", "", "", "", "user1");
        LogRec logRec1 = new LogRec(-1, timestamp, "1", " ", " ", " ", " ", "user1");


        //        int businessId = 1;
//        Request request = new Request(user, businessId,null,null);
//        int businessId = 6;
//        Request request = new Request(user, businessId,null,logisticsRec);
        int businessId = 11;
        Request request = new Request(user, businessId, logRec1, null);


        sendData(request);


    }

    private static void sendData(Request request) throws IOException, ClassNotFoundException {
        Socket client = new Socket("127.0.0.1", 8888);
        try (OutputStream bos = client.getOutputStream()) {
            // 将字节数组写入到Socket管道中
            bos.write(ObjectSerializationUtil.serialize(request));
            client.shutdownOutput();
            System.out.println(receiveResponse(client));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Object receiveResponse(Socket socket) throws IOException, ClassNotFoundException {
        InputStream inputStream = socket.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[4096];
        int bytesRead;

        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        byte[] serializedResponse = outputStream.toByteArray();

        // 关闭输入流
        inputStream.close();

        // 反序列化响应数据
        Object response = ObjectSerializationUtil.deserialize(serializedResponse);

        //关闭socket
        socket.close();
        // 返回响应
        return response;
    }

}