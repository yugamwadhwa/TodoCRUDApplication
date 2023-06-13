package com.ashutosh.todocrud.repository;

import com.ashutosh.todocrud.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {
}
