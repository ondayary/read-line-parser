package com.line.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

public class AWSConnectionMaker implements ConnectionMaker { // 메소드 분리한 것, getConnection만 가져올것이니까
    @Override
    public Connection makeConnection() throws SQLException {
        Map<String, String> env = System.getenv();
        // DB접속 (ex sql workbeanch 실행)
        Connection c = DriverManager.getConnection(env.get("DB_HOST"),
                                                    env.get("DB_USER"),
                                                    env.get("DB_PASSWORD"));
        return c;
    }
}
