package com.taskloom.controller;

import com.taskloom.model.request.TaskCreateRequest;
import com.taskloom.model.request.TaskStatusUpdate;
import com.taskloom.model.request.TaskUpdateRequest;
import com.taskloom.model.response.TaskResponse;
import com.taskloom.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    @GetMapping()
    public ResponseEntity<List<TaskResponse>> getAllTasks(){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getTaskById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid TaskCreateRequest taskCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskCreateRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Integer id, @RequestBody @Valid TaskUpdateRequest taskUpdateRequest) {
       return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskService.updateTask(id, taskUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTaskById(@PathVariable Integer id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponse> updateTaskStatusById(@PathVariable Integer id, @RequestBody @Valid TaskStatusUpdate status){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskService.updateTaskStatusById(id, status));
    }
}
