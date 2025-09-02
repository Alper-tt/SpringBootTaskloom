package com.taskloom.service;

import com.taskloom.entity.TaskEntity;
import com.taskloom.model.TaskStatus;
import com.taskloom.model.request.TaskCreateRequest;
import com.taskloom.model.request.TaskStatusUpdate;
import com.taskloom.model.request.TaskUpdateRequest;
import com.taskloom.model.response.TaskResponse;
import com.taskloom.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;


    public List<TaskResponse> findAll() {
        List<TaskEntity> taskEntities = taskRepository.findAll();
        if(taskEntities.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Task not found");
        }

        return taskEntities.stream()
                .map(taskEntity -> new TaskResponse(
                        taskEntity.getId(),
                        taskEntity.getTitle(),
                        taskEntity.getDescription(),
                        taskEntity.getStatus()
                )).toList();
    }

    public TaskResponse findById(Integer id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        return new TaskResponse(
                taskEntity.getId(),
                taskEntity.getTitle(),
                taskEntity.getDescription(),
                taskEntity.getStatus()
        );
    }

    public TaskResponse createTask(TaskCreateRequest taskCreateRequest) {
        TaskEntity taskEntity = TaskEntity.builder()
                .title(taskCreateRequest.getTitle())
                .description(taskCreateRequest.getDescription())
                .build();

        if(taskCreateRequest.getStatus() != null){
            taskEntity.setStatus(taskCreateRequest.getStatus());
        }
        else{
            taskEntity.setStatus(TaskStatus.TODO);
        }

        TaskEntity savedTask = taskRepository.save(taskEntity);
        return new TaskResponse(
                savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getStatus()
        );
    }

    public TaskResponse updateTask(Integer id, TaskUpdateRequest taskUpdateRequest) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        taskEntity.setTitle(taskUpdateRequest.getTitle());
        taskEntity.setDescription(taskUpdateRequest.getDescription());
        taskEntity.setStatus(taskUpdateRequest.getStatus());
        TaskEntity updatedTask = taskRepository.save(taskEntity);

        return new TaskResponse(
                updatedTask.getId(),
                updatedTask.getTitle(),
                updatedTask.getDescription(),
                updatedTask.getStatus()
        );
    }

    public void deleteTaskById(Integer id) {
        taskRepository.deleteById(id);
    }

    public TaskResponse updateTaskStatusById(Integer id, TaskStatusUpdate status) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        taskEntity.setStatus(status.getTaskStatus());
        TaskEntity updatedTask = taskRepository.save(taskEntity);

        return new TaskResponse(
                updatedTask.getId(),
                updatedTask.getTitle(),
                updatedTask.getDescription(),
                updatedTask.getStatus()
        );
    }
}
