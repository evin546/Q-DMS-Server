package com.q_dms.server.entity;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户信息类
 * 对应数据库表user_info
 */
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 6867499560997346897L;
    private String username;
    private String password;
    private String sex;

    public User(String username, String password, String sex) {
        this.username = username;
        this.password = password;
        this.sex = sex;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}

