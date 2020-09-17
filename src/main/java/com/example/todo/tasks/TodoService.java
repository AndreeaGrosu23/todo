package com.example.todo.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public void addTodo(Todo todo) {
        todoRepository.save(todo);
    }

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public List<Todo> findAllByCompleted(boolean completed) {
        return todoRepository.findAllByCompleted(completed);
    }

    public void toggleStatus(boolean status, int id) {
        todoRepository.updateStatusByID(status, id);
    }

    public void removeCompleted() {
        todoRepository.removeTodoByCompletedIsTrue();
    }

    public void deleteById(int id) {
        todoRepository.deleteById(id);
    }

    public Optional<Todo> getById(int id) {
        return todoRepository.findById(id);
    }

    public void updateStatus(boolean completed) {
        todoRepository.updateStatus(completed);
    }

    public void updateTitleById(String title, int id) {
        todoRepository.updateTitleByID(title, id);
    }

}
