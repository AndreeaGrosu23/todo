package com.example.todo.repository;

import com.example.todo.model.TodoUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<TodoUser, Long> {
    Optional<TodoUser> findByUsername(String username);
}
