package com.ashutosh.todocrud.repository;

import com.ashutosh.todocrud.entity.Todo;
import com.ashutosh.todocrud.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ToDoRepositoryTest {

    @Autowired
    ToDoRepository toDoRepository;

    @Test
    public void saveToDoTest(){
        Todo todo = new Todo();

        todo.setContent("Test Todo");

        toDoRepository.save(todo);

        List<Todo> todoList = toDoRepository.findAll();

        assertNotNull(todoList);

        assertTrue(todoList.contains(todo));
    }

}