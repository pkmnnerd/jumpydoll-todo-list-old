package com.jumpydoll.app.pkmnnerd.jumpydolltodolist.controller;

import com.jumpydoll.app.pkmnnerd.jumpydolltodolist.model.Task;
import com.jumpydoll.app.pkmnnerd.jumpydolltodolist.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TodoController {
    @Autowired
    TaskRepository taskRepository;

    @GetMapping(value = "/api/tasks")
    public ResponseEntity<?> getTasks() {
        List<Task> todoList = taskRepository.findAll();
        return new ResponseEntity<>(todoList, HttpStatus.OK);
    }

    @PostMapping(value = "/api/tasks", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        task.setCreateTime(System.currentTimeMillis());
        task.setFinished(false);
        task.setId(null);
        Task savedItem = taskRepository.insert(task);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/tasks/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateTask(@PathVariable("id") String id, @RequestBody Task task) {
        Optional<Task> existingTaskOptional = taskRepository.findById(id);
        if (existingTaskOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Task existingTask = existingTaskOptional.get();
        if (!id.equals(task.getId())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        existingTask.setDescription(task.getDescription());
        existingTask.setFinished(task.isFinished());
        Task savedItem = taskRepository.save(existingTask);
        return new ResponseEntity<>(savedItem, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/tasks/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable("id") String id) {
        Optional<Task> existingTask = taskRepository.findById(id);
        if (existingTask.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        taskRepository.delete(existingTask.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
