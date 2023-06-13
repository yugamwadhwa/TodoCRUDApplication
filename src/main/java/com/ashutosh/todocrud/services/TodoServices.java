package com.ashutosh.todocrud.services;

import com.ashutosh.todocrud.entity.Todo;
import com.ashutosh.todocrud.entity.Users;
import com.ashutosh.todocrud.repository.ToDoRepository;
import com.ashutosh.todocrud.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TodoServices {

    private final UserServices userServices;
    private final ToDoRepository toDoRepository;

    private final UserRepository userRepository;

    public TodoServices(UserServices userServices, ToDoRepository toDoRepository,UserRepository userRepository) {
        this.userServices = userServices;
        this.toDoRepository = toDoRepository;
        this.userRepository = userRepository;
    }

    public Todo getTodoById(Long todoId){
        return toDoRepository.findById(todoId).orElseThrow(() -> new NoSuchElementException());
    }

    public void addTodo(Long userId, Todo todo){
        Users user = userServices.getUserById(userId);
        user.getTodoList().add(todo);
        toDoRepository.save(todo);
        userRepository.save(user);
    }


    public void toggleTodoCompleted(Long todoId){
        Todo todo = this.getTodoById(todoId);
        todo.setCompleted(!todo.getCompleted());
        toDoRepository.save(todo);
    }

    public void deleteTodo(Long userId,Long todoId){
        Users user = userServices.getUserById(userId);
        Todo todo = this.getTodoById(todoId);
        user.getTodoList().remove(todo);
        userRepository.save(user);
        toDoRepository.delete(todo);
    }


}
