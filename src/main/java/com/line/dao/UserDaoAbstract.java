package com.line.dao;

import com.line.domain.User;

import java.sql.*;
import java.util.Map;

public abstract class UserDaoAbstract { // UserDao 를 copy한 아이
                                        // 구현체를 싹 뺀 아이
    public abstract Connection makeConnection() throws SQLException;

    public void add(User user) {
        Map<String, String> env = System.getenv();
        try {
            // DB접속 (ex sql workbeanch 실행)
            Connection c = makeConnection();
            /*
            Connection c = DriverManager.getConnection(env.get("DB_HOST"),
                                                       env.get("DB_USER"),
                                                       env.get("DB_PASSWORD")); */
            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());

            // Query문 실행
            pstmt.executeUpdate();

            pstmt.close();
            c.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findById(String id) {
        Map<String, String> env = System.getenv();
        Connection c;
        try {
            // DB접속 (ex sql workbeanch 실행)
            c = makeConnection();
            // 이 부분은 반복이 되기 때문에 분리 해놓는 것이 좋다.
            /*
            c = DriverManager.getConnection(env.get("DB_HOST"),
                                            env.get("DB_USER"),
                                            env.get("DB_PASSWORD")); */

            PreparedStatement pstmt = c.prepareStatement(("SELECT * FROM users WHERE id = ?"));
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            rs.next();
            User user = new User(rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("password"));
            rs.close();
            pstmt.close();
            c.close();

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // makeConnection() 분리
    // 접근제어자 : private / Return type : Connection
    /*private Connection makeConnection() throws SQLException {
        Map<String, String> env = System.getenv();
        // DB접속 (ex sql workbeanch 실행)
        Connection c = DriverManager.getConnection(env.get("DB_HOST"),
                                                   env.get("DB_USER"),
                                                   env.get("DB_PASSWORD"));
        return c;
    }*/
}
