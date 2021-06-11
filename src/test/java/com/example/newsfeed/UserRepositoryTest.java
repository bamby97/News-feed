package com.example.newsfeed;


import com.example.newsfeed.models.User;
import com.example.newsfeed.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.Mockito.when;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    public void init(){
        entityManager.persistAndFlush(new User(
                "josh"
        ));
        entityManager.persistAndFlush(new User(
                "tom"
        ));
    }

    @Test
    void getUser_expectUser(){
        User savedUser=entityManager.persistAndFlush(new User(
                "tom"
        ));


        User user=userRepository.getById(savedUser.getId());

        then(user).isEqualTo(savedUser);
    }

    @Test
    @Transactional
    void followUser_expectFollowUser(){
        User user1=entityManager.persistAndFlush(new User(
                "tom"
        ));
        User user2=entityManager.persistAndFlush(new User(
                "josh"
        ));

        User savedUser1= userRepository.findById(user1.getId()).get();
        User savedUser2=userRepository.findById(user2.getId()).get();
        savedUser1.getFollowing().add(savedUser2);

        //List<User> folllowers=savedUser2.getFollowers()
        then(userRepository.findById(savedUser1.getId()).get().getFollowing()).contains(savedUser2);
    }
}
