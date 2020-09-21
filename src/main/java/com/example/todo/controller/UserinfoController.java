package com.example.todo.controller;

import com.example.todo.model.TodoUser;
import com.example.todo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class UserinfoController {

//    private UserRepository userRepository;
//
//    @Autowired
//    public UserinfoController(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @GetMapping("/me")
    public String currentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getPrincipal());

        TodoUser todoUser = (TodoUser) authentication.getPrincipal();

//        TodoUser todoUser = userRepository.findByUsername(authentication.getPrincipal().toString())
//                .orElseThrow(() -> new UsernameNotFoundException("Username: " + authentication.getPrincipal().toString() + " not found"));
        return todoUser.getUsername() + "\n" + todoUser.getRoles();
    }
}
