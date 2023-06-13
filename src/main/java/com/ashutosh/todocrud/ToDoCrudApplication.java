package com.ashutosh.todocrud;

import com.ashutosh.todocrud.entity.Todo;
import com.ashutosh.todocrud.entity.Users;
import com.ashutosh.todocrud.repository.ToDoRepository;
import com.ashutosh.todocrud.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ToDoCrudApplication implements CommandLineRunner {

    private final UserRepository userRepository;

    private final ToDoRepository toDoRepository;

    public ToDoCrudApplication(UserRepository userRepository, ToDoRepository toDoRepository) {
        this.userRepository = userRepository;
        this.toDoRepository = toDoRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ToDoCrudApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        Users users = new Users();
        users.setPassword("should be hashed");
        users.setUsername("Ashu");

        Todo todo = new Todo();
        todo.setContent("Coding Ninjas Assignment");

        users.getTodoList().add(todo);

        toDoRepository.save(todo);
        userRepository.save(users);
    }
}
