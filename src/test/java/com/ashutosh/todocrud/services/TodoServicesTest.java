package com.ashutosh.todocrud.services;

import com.ashutosh.todocrud.entity.Todo;
import com.ashutosh.todocrud.entity.Users;
import com.ashutosh.todocrud.repository.ToDoRepository;
import com.ashutosh.todocrud.repository.UserRepository;
import org.h2.engine.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TodoServicesTest {

    @Mock
    private ToDoRepository toDoRepository;

    @Mock
    private UserRepository userRepository;
    private UserServices userServices;
    private TodoServices todoServices;

    private Todo todo;
    private Users user;

    @BeforeEach
    void setUp() {
        todo = new Todo();
        todo.setId(1L);
        todo.setContent("test Todo");
        user = new Users();
        user.setId(1L);
        user.setUsername("ashutosh");
        user.setPassword("password");
        this.userServices = new UserServices(userRepository);
        this.todoServices = new TodoServices(userServices,toDoRepository,userRepository);
    }

    @Test
    void getTodoByIdSuccess() {
        when(toDoRepository.findById(1L)).thenReturn(Optional.ofNullable(todo));
        Assertions.assertEquals(todo,todoServices.getTodoById(1L));
    }

    @Test
    void getTodoByIdFailure() {
        Assertions.assertThrows(NoSuchElementException.class,() -> todoServices.getTodoById(2L));
    }

    @Test
    void addTodoSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        todoServices.addTodo(1L,todo);
        verify(toDoRepository,times(1)).save(todo);
    }

    @Test
    void addTodoFailure() {
        when(userRepository.findById(1L)).thenThrow(new RuntimeException());
        Assertions.assertThrows(RuntimeException.class,() -> todoServices.addTodo(2L,todo));
    }

    @Test
    void toggleTodoCompletedSuccess() {
        when(toDoRepository.findById(1L)).thenReturn(Optional.ofNullable(todo));
        Boolean beforeToggle = todo.getCompleted();
        todoServices.toggleTodoCompleted(1L);
        verify(toDoRepository,times(1)).save(todo);
        Assertions.assertNotEquals(beforeToggle,todo.getCompleted());
    }

    @Test
    void toggleTodoCompletedFailure() {
        Assertions.assertThrows(NoSuchElementException.class,() -> todoServices.toggleTodoCompleted(2L));
    }

    @Test
    void deleteTodoSuccess() {
        user.getTodoList().add(todo);
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(user));
        when(toDoRepository.findById(1L)).thenReturn(Optional.ofNullable(todo));
        todoServices.deleteTodo(1L,1L);
        verify(userRepository,times(1)).save(user);
        verify(toDoRepository,times(1)).delete(todo);
        Assertions.assertFalse(user.getTodoList().contains(todo));
    }

    @Test
    void deleteTodoFailure() {
        Assertions.assertThrows(NoSuchElementException.class,()-> todoServices.deleteTodo(2L,2L));
    }
}