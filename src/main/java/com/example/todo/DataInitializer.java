package com.example.todo;

import com.example.todo.model.Todo;
import com.example.todo.model.TodoUser;
import com.example.todo.repository.TodoRepository;
import com.example.todo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final TodoRepository todoRepository;

    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, TodoRepository todoRepository) {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void run(String... args) {
        log.debug("initializing sample data...");
        Arrays.asList("todo1", "todo2", "todo3").forEach(t -> this.todoRepository.saveAndFlush(Todo.builder().title(t).build()));

        log.debug("printing all vehicles...");
        todoRepository.findAll().forEach(v -> log.debug(" Task :" + v.toString()));

        userRepository.save(TodoUser.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .roles(Arrays.asList("ROLE_USER"))
                .build()
        );

        userRepository.save(TodoUser.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
                .build()
        );

        log.debug("printing all users...");
        userRepository.findAll().forEach(todoUser -> log.debug(" TodoApp User :" + todoUser.toString()));

    }
}
