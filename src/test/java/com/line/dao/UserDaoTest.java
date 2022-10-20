package com.line.dao;

import com.line.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class) // test코드에서 spring을 쓰기위함이고 안쓰면 autowired가 실행이 안된다.
@ContextConfiguration(classes = UserDaoFactory.class) // 이 어노테이션은 특정 클래스만 불러서 실행하는 것
/*
Test Code Refactoring
테스트 코드에서 Spring ApplicationContext를 사용하기 위해서는
@ExtendsWith()
@ContextConfiguraiton() 를 추가 해주어야 합니다.
 */
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    UserDao userDao;

    @BeforeEach
    void setUp() {
        this.userDao = context.getBean("awsUSerDao", UserDao.class);
        // 각 @Test 메소드가 실행 될때마다 실행되는 코드가 있습니다.
        // 그 부분을 Junit에서 공통화 시킬 수 있게 제공하는 기능이 BeforeEach입니다.

        // ApplicationContext에서 Bean을 가지고 올때 spring이 검색 하는 방법
//        User user1 = new User("1", "lee", "lee");
//        User user2 = new User("2", "da", "da");
//        User user3 = new User("3", "on", "on");
    }

    // addAndGet() 리팩토링
    @Test
    void addAndGet() throws SQLException {
        User user1 = new User("1", "lee", "lee");

        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        User user = userDao.findById(user1.getId());

        assertEquals(user1.getName(), user.getName());
        assertEquals(user1.getPassword(), user.getPassword());

    }

    // getCount(), deleteAll() 테스트 추가
/*    @Test
    void addAndGet() throws SQLException {
        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        String id = "9";
        userDao.add(new User(id, "test", "test"));
        assertEquals(1, userDao.getCount());
        User user = userDao.findById(id);

        assertEquals("test", user.getName());
        assertEquals("test", user.getPassword());

    }*/

    @Test
    void count() throws SQLException {
        User user1 = new User("1", "lee", "lee");
        User user2 = new User("2", "da", "da");
        User user3 = new User("3", "on", "on");

        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        userDao.deleteAll();
        assertEquals(0, userDao.getCount());

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        userDao.add(user2);
        assertEquals(2, userDao.getCount());
        userDao.add(user3);
        assertEquals(3, userDao.getCount());
    }

    @Test
    void addAndSelect() { // add, select 둘다 테스트
//        UserDao userDao = new UserDao(); // add, select 둘다 Dao클래스 안에 있기 때문에 Dao를 객체 생성 해줘야 한다.

        // 인터페이스 기능 추가한 후
//        AWSUserDaoImpl userDao = new AWSUserDaoImpl(); // abstract 적용

        // 근데 abstract 추상클래스를 잘 안쓰고 인터페이스를 쓴다.
        // 추가하고 싶은 기능만 인터페이스에 쓰고, 추상클래스는 상속을 써야해서 (단점:단일상속) 복잡해진다.

        // Factory 기능 추가한 후
//        UserDao userDao = new UserDaoFactory().awsUserDao();
        UserDao userDao = context.getBean("awsUserDao", UserDao.class);
        // add
        String id = "8";
        userDao.add(new User(id, "test", "test"));
        /*
        User user = new User(id, "text", "test");
        userDao.add(user);
         */

        // select
        User user = userDao.findById(id);
        Assertions.assertEquals("test",user.getName());
        Assertions.assertEquals("test",user.getPassword());
    }
}