package com.ashutosh.todocrud.controller;

import com.ashutosh.todocrud.entity.Todo;
import com.ashutosh.todocrud.entity.Users;
import com.ashutosh.todocrud.services.TodoServices;
import com.ashutosh.todocrud.services.UserServices;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final TodoServices todoServices;
    private final UserServices userServices;

    public UserController(UserServices userServices,TodoServices todoServices){
        this.userServices = userServices;
        this.todoServices = todoServices;
    }

    @GetMapping("/{userId}")
    public Users getUserById(@PathVariable Long userId){
        return userServices.getUserById(userId);
    }

    @PostMapping
    public void addUser(@RequestBody Users userRequest){
        userServices.addUser(userRequest);
    }

    @PostMapping("/{userId}/todos")
    public void addTodo(@PathVariable Long userId, @RequestBody Todo todo){
        todoServices.addTodo(userId,todo);
    }

    @PostMapping("/todos/{todoId}")
    public void toggleTodoCompleted(@PathVariable Long todoId){
        todoServices.toggleTodoCompleted(todoId);
    }

    @DeleteMapping("{userId}/todos/{todoId}")
    public void deleteTodo(@PathVariable Long userId,@PathVariable Long todoId){
        todoServices.deleteTodo(userId,todoId);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId){
        userServices.deleteUser(userId);
    }
}
