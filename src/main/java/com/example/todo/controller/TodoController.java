package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping
public class TodoController {

    private TodoRepository todoRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository=todoRepository;
    }

    @GetMapping("/hello")
    public String greet() {
        return "hello";
    }


    // Add new task
    @PostMapping("/addTodo")
    public ResponseEntity addTodo(@RequestParam("todo-title") String title) {
        Todo newTodo = new Todo(title);
        todoRepository.save(newTodo);
        return ResponseEntity.noContent().build();
    }

    // List all tasks
    @PostMapping("/list")
    public ResponseEntity getAllTodos(@RequestParam("status") String status) {
        if(status.isEmpty()) {
            return ResponseEntity.ok(this.todoRepository.findAll());
        } else if (status.equals("active")) {
            return ResponseEntity.ok(this.todoRepository.findAllByCompleted(false));
        } else if (status.equals("complete")) {
            return ResponseEntity.ok(this.todoRepository.findAllByCompleted(true));
        }
        return ResponseEntity.noContent().build();
    }

    // Delete all completed tasks
    @DeleteMapping("/todos/completed")
    public ResponseEntity removeCompleted() {
        todoRepository.removeCompleted();
        return ResponseEntity.noContent().build();
    }

    //Toggle status for all
    @PutMapping("/todos/toggle_all")
    public ResponseEntity toggleAll(@RequestParam("toggle-all") String complete) {
        boolean completed = complete.equals("true");

        if(completed) {
            todoRepository.updateStatus(true);
        } else {
            todoRepository.updateStatus(false);
        }
        return ResponseEntity.noContent().build();
    }

    // Delete task by id
    @DeleteMapping("/todos/{id}")
    public ResponseEntity deleteById(@PathVariable("id") int id) {
        todoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Update title by ID
    @PutMapping("/todos/{id}")
    public ResponseEntity updateTitleById(@PathVariable("id") int id, @RequestParam("todo-title") String title) {
        if (todoRepository.findById(id).isPresent()) {
            todoRepository.updateTitleById(title,id);
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>("Todo not found", HttpStatus.NOT_FOUND);
    }

    // Find task by id
    @GetMapping("/todos/{id}")
    public ResponseEntity getById(@PathVariable("id") int id) {
        if (todoRepository.findById(id).isPresent()) {
            return ResponseEntity.ok(todoRepository.findById(id));
        }
        return new ResponseEntity<>("Todo not found", HttpStatus.NOT_FOUND);
    }

    // Toggle the task status by id
    @PutMapping("/todos/{id}/toggle_status")
    public ResponseEntity toggleStatus(@PathVariable("id") int id, @RequestParam("status") String status) {
        boolean isCompleted = status.equals("true");
        todoRepository.updateStatusById(isCompleted, id);
        return ResponseEntity.noContent().build();
    }
}
