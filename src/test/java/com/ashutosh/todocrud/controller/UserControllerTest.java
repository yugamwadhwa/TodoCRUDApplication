package com.ashutosh.todocrud.controller;

import com.ashutosh.todocrud.entity.Todo;
import com.ashutosh.todocrud.entity.Users;
import com.ashutosh.todocrud.repository.ToDoRepository;
import com.ashutosh.todocrud.repository.UserRepository;
import com.ashutosh.todocrud.services.TodoServices;
import com.ashutosh.todocrud.services.UserServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServices userServices;

    @MockBean
    private TodoServices todoServices;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ToDoRepository toDoRepository;

    private Users user;
    private Todo todo;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        user = new Users(1L,"test","password");
        todo = new Todo("testing todo");
        ObjectMapper objectMapper = new ObjectMapper();
        String userJSON = objectMapper.writeValueAsString(user);
        String todoJSON = objectMapper.writeValueAsString(todo);
    }

    @Test
    void getUserById() throws Exception {
        when(userServices.getUserById(1L)).thenReturn(user);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users/1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(user)));
    }

    @Test
    void addUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String userJSON = objectMapper.writeValueAsString(user);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users").contentType(MediaType.APPLICATION_JSON).content(userJSON)).andExpect(status().isOk());
    }

    @Test
    void addTodo() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String todoJSON = objectMapper.writeValueAsString(todo);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/1/todos").contentType(MediaType.APPLICATION_JSON).content(todoJSON)).andExpect(status().isOk());
    }

    @Test
    void toggleTodoCompleted() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/users/todos/1")).andExpect(status().isOk());
    }

    @Test
    void deleteTodo() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/users/1/todos/1")).andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")).andExpect(status().isOk());
    }
}