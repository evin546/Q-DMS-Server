package com.q_dms.server;

import com.q_dms.server.network.Server;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        //启动服务端
        Server.clientRequestHandler();
    }


}
