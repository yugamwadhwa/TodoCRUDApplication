package com.ashutosh.todocrud.repository;

import com.ashutosh.todocrud.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<Todo,Long> {
}
