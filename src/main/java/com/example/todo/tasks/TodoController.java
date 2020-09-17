package com.example.todo.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // Add new task
    @PostMapping("/addTodo")
    public void addTodo(@RequestParam("todo-title") String title) {
        Todo newTodo = new Todo(title);
        todoService.addTodo(newTodo);
    }

    // List all tasks
    @PostMapping("/list")
    public List<Todo> getAllTodos(@RequestParam("status") String status) {
        if(status.isEmpty()) {
            return todoService.findAll();
        } else if (status.equals("active")) {
            return todoService.findAllByCompleted(false);
        } else if (status.equals("complete")) {
            return todoService.findAllByCompleted(true);
        }
        return null;
    }

    // Delete all completed tasks
    @DeleteMapping("/todos/completed")
    public void removeCompleted() {
        todoService.removeCompleted();
    }

    //Toggle status for all
    @PutMapping("/todos/toggle_all")
    public void toggleAll(@RequestParam("toggle-all") String complete) {
        boolean completed = complete.equals("true");

        if(completed) {
            todoService.updateStatus(true);
        } else {
            todoService.updateStatus(false);
        }
    }

    // Delete task by id
    @DeleteMapping("/todos/{id}")
    public void deleteById(@PathVariable("id") int id) {
        todoService.deleteById(id);
    }

    // Update title by ID
    @PutMapping("/todos/{id}")
    public void updateTitleById(@PathVariable("id") int id, @RequestParam("todo-title") String title) {
        todoService.updateTitleById(title,id);
    }

    // Find task by id
    @GetMapping("/todos/{id}")
    public Optional<Todo> getById(@PathVariable("id") int id) {
        return todoService.getById(id);
    }

    // Toggle the task status by id
    @PutMapping("/todos/{id}/toggle_status")
    public void toggleStatus(@PathVariable("id") int id, @RequestParam("status") String status) {
        boolean isCompleted = status.equals("true");
        todoService.toggleStatus(isCompleted, id);
    }
}
