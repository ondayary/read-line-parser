package com.line.dao;

import com.line.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserDao {

//        AWSConnectionMaker awsConnectionMaker; // 모든 connection 넣을 수 있는데 얘기를 해줘야한다. 그건 constructor로 알려줘야 한다.
    private ConnectionMaker connectionMaker;

    public UserDao() { // 얘기를 해주는 구문
        this.connectionMaker = new AWSConnectionMaker();
    }

    public UserDao(ConnectionMaker connectionMaker) { // 매개변수 1개 있는 생성자
        this.connectionMaker = connectionMaker;
    }
    public void add(User user) { // 데이터베이스에 넣는게 결과라 리턴 타입은 없다.
                                 // user라는 클래스를 매개변수로 받은 이유는 매개변수를 넣고 싶을 때마다 작성할 수 없어서 클래스를 만들어 분리했다.

        // 노출을 막기 위해 환경변수 Map을 이용해 만든다.
        Map<String, String> env = System.getenv(); // 시스템을 이용해 환경변수를 가져와 맵에 저장한다.
        try {
            // DB접속 (ex sql workbeanch 실행)
            Connection c = connectionMaker.makeConnection();
            /*
            Connection c = DriverManager.getConnection(env.get("DB_HOST"),
                                                       env.get("DB_USER"),
                                                       env.get("DB_PASSWORD")); */
            // Query문 준비 후 작성
            PreparedStatement pstmt = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
            pstmt.setString(1, user.getId()); // 준비된 자리에 채워넣는다.
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());

            // Query문 실행
            pstmt.executeUpdate();

            // 닫아야하는데 연것의 역순으로
            pstmt.close();
            c.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 안에 정보가 무엇인지 빼서 확인하는 함수
    public User findById(String id) { // 리턴타입이 User 타입인 이유는?
        Map<String, String> env = System.getenv(); // 첫번째가 데이터베이스에 연결하는 것이다.
        Connection c;
        try {
            // DB접속 (ex sql workbeanch 실행)
            c = connectionMaker.makeConnection();
            // 이 부분은 반복이 되기 때문에 분리 해놓는 것이 좋다.
            /*
            c = DriverManager.getConnection(env.get("DB_HOST"),
                                            env.get("DB_USER"),
                                            env.get("DB_PASSWORD")); */

            // sql구문을 쓸 수 있는 구문을 가져온다.
            PreparedStatement pstmt = c.prepareStatement(("SELECT * FROM users WHERE id = ?"));
            pstmt.setString(1, id); // 매개변수로 String id를 넣었기 때문에 여기에 이렇게 작성이 가능하다.

            // 가져오는 것
            // pstmt를 가져와서 실행한 후에 resultSet에 넣는다.
            ResultSet rs = pstmt.executeQuery();
            rs.next(); // 쿼리를 실행한 다음에 더 읽어올 것이 있는지 없는지 확인하는 작업을 거쳐야 close가 가능하다.
                       // rs에는 원하는 정보가 들어있다.
            User user = new User(rs.getString("id"),
                                 rs.getString("name"),
                                 rs.getString("password"));
            rs.close();
            pstmt.close();
            c.close();

            // 결과값이 없을때 exception 처리
            /*
            결과값이 없으면 rs.next()했을 때 ResultSet에 null값이 들어갑니다.
            그래서 null이 아닐때(값이 있을 때)에만 User 오브젝트를 생성하도록 수정 합니다.
            그리고 User오브젝트가 null인채로 끝나면 에러를 던져 줍니다.
             */
            if(user == null) throw new EmptyResultDataAccessException(1);

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void findById() {
        assertThrows(EmptyResultDataAccessException.class, () -> {
            UserDao userDao = new UserDao();
            userDao.findById("30");
        });
    }

    public int getCount() throws SQLException {
        Connection c = null;
        PreparedStatement pstmt;

        c = connectionMaker.makeConnection();
        pstmt = c.prepareStatement("SELECT COUNT(*) FROM users");
        ResultSet rs = pstmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        pstmt.close();
        c.close();
        return count;
    }


    public void deleteAll() throws SQLException {
        Connection c = null;
        PreparedStatement pstmt = null;

        // connection, PreparedStatement할때 에러가 나도 ps.close(), c.close()를 하기 위한 처리
        try { // 단축키 : opt + cmd + T
            c = connectionMaker.makeConnection();
            pstmt = c.prepareStatement("DELETE FROM users"); // delete구문 작성
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally { // 에러가 나도 실행되는 블럭
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
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
    // makeConnection()을 삭제 하고
    // AwsConnectionMaker추가
    // 그리고 초기화

    public static void main(String[] args) {
        UserDao userDao = new UserDao();

        // User를 parameter로 받도록 add메소드 수정
        // 받은 user를 setString에서 활용하게 수정
        // main에서 new User() 를 해서 user를 넘기게 수정
        userDao.add(new User("1", "test","test")); // 객체가 없으니까 바로 괄호안에서 만들어서 입력하기

        User user = userDao.findById("6"); // user라는 변수에 넣어줘야 한다.
        System.out.println(user.getName());
    }
}


/*
리팩토링 진행 단계
1. method로 분리한다
2. Class로 분리한다
3. Interface를 적용한다
*/