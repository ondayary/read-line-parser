package com.line.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LocalConnectionMaker implements ConnectionMaker{
    @Override
    public Connection makeConnection() throws SQLException {

        // DB접속 (ex sql workbeanch실행)
        Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/likelion-db",
                "root",
                "12345678");
        return c;
    }
}
