package com.ashutosh.todocrud.repository;

import com.ashutosh.todocrud.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Test
    public void saveUserTest(){
        Users user = new Users();
        user.setUsername("Test");
        user.setPassword("password");

        userRepository.save(user);

        List<Users> usersList = userRepository.findAll();

        assertNotNull(usersList);

        assertTrue(usersList.contains(user));
    }

}