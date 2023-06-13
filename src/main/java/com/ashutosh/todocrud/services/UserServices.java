package com.ashutosh.todocrud.services;

import com.ashutosh.todocrud.entity.Users;
import com.ashutosh.todocrud.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserServices {

    private final UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users getUserById (Long userId){
        return userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
    }

    public void addUser(Users user){
         userRepository.save(user);
    }

    public void deleteUser(Long userId){
        if(!userRepository.existsById(userId))
            throw new NoSuchElementException();
        userRepository.deleteById(userId);
    }

}
