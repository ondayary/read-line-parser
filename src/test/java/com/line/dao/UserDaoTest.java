package com.line.dao;

import com.line.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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