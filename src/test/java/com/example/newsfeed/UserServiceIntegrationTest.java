package com.example.newsfeed;

import com.example.newsfeed.exceptions.NoUserFoundException;
import com.example.newsfeed.models.User;
import com.example.newsfeed.repository.UserRepository;
import com.example.newsfeed.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Transactional
public class UserServiceIntegrationTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Test()
    public void followUserTest_expectException(){
        Throwable throwable=catchThrowable(()->userService.followUser(12L,34L));
        then(throwable).isInstanceOf(NoUserFoundException.class);

    }

    @Test()
    public void followUserTest(){
        User user1=userRepository.save(new User("josh"));
        User user2=userRepository.save(new User("mike"));

        then(userService.followUser(user1.getId(),user2.getId())).isEqualTo(true);
    }
}
