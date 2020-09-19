package com.example.todo.repository;

import com.example.todo.model.TodoAppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<TodoAppUser, Long> {
}