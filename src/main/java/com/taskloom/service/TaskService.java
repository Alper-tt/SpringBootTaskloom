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

    private TaskResponse toResponse(TaskEntity e){
        return new TaskResponse(e.getId(), e.getTitle(), e.getDescription(), e.getStatus());
    }

    public List<TaskResponse> findAll() {
        List<TaskEntity> taskEntities = taskRepository.findAll();
        if(taskEntities.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Task not found");
        }

        return taskEntities.stream()
                .map(this::toResponse)
                .toList();
    }

    public TaskResponse findById(Integer id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        return toResponse(taskEntity);
    }

    public TaskResponse createTask(TaskCreateRequest taskCreateRequest) {
        TaskEntity taskEntity = TaskEntity.builder()
                .title(taskCreateRequest.getTitle())
                .description(taskCreateRequest.getDescription())
                .status(taskCreateRequest.getStatus() != null ? taskCreateRequest.getStatus() : TaskStatus.TODO)
                .build();

        return toResponse(taskRepository.save(taskEntity));
    }

    public TaskResponse updateTask(Integer id, TaskUpdateRequest taskUpdateRequest) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        taskEntity.setTitle(taskUpdateRequest.getTitle());
        taskEntity.setDescription(taskUpdateRequest.getDescription());
        taskEntity.setStatus(taskUpdateRequest.getStatus() != null ? taskUpdateRequest.getStatus() : TaskStatus.TODO);

        return toResponse(taskRepository.save(taskEntity));
    }

    public void deleteTaskById(Integer id) {
        if(!taskRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        taskRepository.deleteById(id);
    }

    public TaskResponse updateTaskStatusById(Integer id, TaskStatusUpdate status) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        taskEntity.setStatus(status.getTaskStatus());
        return toResponse(taskRepository.save(taskEntity));
    }
}
