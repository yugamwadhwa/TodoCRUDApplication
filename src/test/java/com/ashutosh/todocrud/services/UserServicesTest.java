package com.ashutosh.todocrud.services;

import com.ashutosh.todocrud.entity.Users;
import com.ashutosh.todocrud.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServicesTest {

    @Mock
    private UserRepository userRepository;
    private UserServices userServices;

    private Users user;

    @BeforeEach
    void setUp() {
        user = new Users("ashutosh","password");
        user.setId(1L);
        this.userServices = new UserServices(userRepository);
    }

    @Test
    void getUserByIdWhenExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        Assertions.assertEquals(user,userServices.getUserById(1L));
    }

    @Test
    void getUserByIdWhenNotExists(){
        Assertions.assertThrows(NoSuchElementException.class,() -> userServices.getUserById(2L));
    }
    @Test
    void addUserSuccess() {
        userServices.addUser(user);
        verify(userRepository,times(1)).save(user);
    }

    @Test
    void addUserFailure() {
        when(userRepository.save(user)).thenThrow(new RuntimeException());
        Assertions.assertThrows(RuntimeException.class,() -> userServices.addUser(user));
    }

    @Test
    void deleteUserSuccess() {
        when(userRepository.existsById(1L)).thenReturn(true);
        userServices.deleteUser(1L);
        verify(userRepository,times(1)).deleteById(1L);
    }

    @Test
    void deleteUserFailure() {
        Assertions.assertThrows(NoSuchElementException.class,() -> userServices.deleteUser(2L));
    }
}