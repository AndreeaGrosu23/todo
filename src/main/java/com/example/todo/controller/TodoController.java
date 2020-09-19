package com.example.todo.controller;

import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class TodoController {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // Add new task
    @PostMapping("/addTodo")
    public void addTodo(@RequestParam("todo-title") String title) {
        Todo newTodo = new Todo(title);
        todoRepository.save(newTodo);
    }

    // List all tasks
    @PostMapping("/list")
    public List<Todo> getAllTodos(@RequestParam("status") String status) {
        if(status.isEmpty()) {
            return todoRepository.findAll();
        } else if (status.equals("active")) {
            return todoRepository.findAllByCompleted(false);
        } else if (status.equals("complete")) {
            return todoRepository.findAllByCompleted(true);
        }
        return null;
    }

    // Delete all completed tasks
    @DeleteMapping("/todos/completed")
    public void removeCompleted() {
        todoRepository.removeCompleted();
    }

    //Toggle status for all
    @PutMapping("/todos/toggle_all")
    public void toggleAll(@RequestParam("toggle-all") String complete) {
        boolean completed = complete.equals("true");

        if(completed) {
            todoRepository.updateStatus(true);
        } else {
            todoRepository.updateStatus(false);
        }
    }

    // Delete task by id
    @DeleteMapping("/todos/{id}")
    public void deleteById(@PathVariable("id") int id) {
        todoRepository.deleteById(id);
    }

    // Update title by ID
    @PutMapping("/todos/{id}")
    public void updateTitleById(@PathVariable("id") int id, @RequestParam("todo-title") String title) {
        todoRepository.updateTitleById(title,id);
    }

    // Find task by id
    @GetMapping("/todos/{id}")
    public Optional<Todo> getById(@PathVariable("id") int id) {
        return todoRepository.findById(id);
    }
    // Toggle the task status by id
    @PutMapping("/todos/{id}/toggle_status")
    public void toggleStatus(@PathVariable("id") int id, @RequestParam("status") String status) {
        boolean isCompleted = status.equals("true");
        todoRepository.updateStatusById(isCompleted, id);
    }
}
