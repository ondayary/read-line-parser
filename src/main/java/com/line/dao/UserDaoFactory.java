package com.line.dao;

import com.line.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 스프링은 여기서 시작한다.

public class UserDaoFactory {
    // 관계 설정 기능 - 어떤 조합을 사용할 것인지 ?
    // 조립을 해준다.
    @Bean
    public UserDao awsUserDao() {
        AWSConnectionMaker awsConnectionMaker = new AWSConnectionMaker();
        UserDao userDao = new UserDao(awsConnectionMaker);
        return userDao;
    }

    @Bean
    public UserDao localUserDao() {
        UserDao userDao = new UserDao(new LocalConnectionMaker());
        return userDao;
    }
}
