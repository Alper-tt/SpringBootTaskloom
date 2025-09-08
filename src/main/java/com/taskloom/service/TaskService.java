package com.taskloom.service;

import com.taskloom.entity.TaskEntity;
import com.taskloom.entity.UserEntity;
import com.taskloom.model.TaskStatus;
import com.taskloom.model.request.TaskCreateRequest;
import com.taskloom.model.request.TaskStatusUpdate;
import com.taskloom.model.request.TaskUpdateRequest;
import com.taskloom.model.response.TaskResponse;
import com.taskloom.repository.TaskRepository;
import com.taskloom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskResponse taskEntityToTaskResponse(TaskEntity e){
        return new TaskResponse(e.getId(),
                e.getTitle(),
                e.getDescription(),
                e.getStatus(),
                e.getCreatedAt(),
                e.getUpdatedAt());
    }

    public List<TaskResponse> findAll() {
        List<TaskEntity> taskEntities = taskRepository.findAll();
        if(taskEntities.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Task not found");
        }

        return taskEntities.stream()
                .map(this::taskEntityToTaskResponse)
                .toList();
    }

    public TaskResponse findById(Integer id) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        return taskEntityToTaskResponse(taskEntity);
    }

    public TaskResponse createTask(TaskCreateRequest taskCreateRequest) {
        UserEntity user = userRepository.findById(taskCreateRequest.getAssignedUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));

        TaskEntity taskEntity = TaskEntity.builder()
                .title(taskCreateRequest.getTitle())
                .description(taskCreateRequest.getDescription())
                .status(taskCreateRequest.getStatus() != null ? taskCreateRequest.getStatus() : TaskStatus.TODO)
                .user(user)
                .build();

        return taskEntityToTaskResponse(taskRepository.save(taskEntity));
    }

    public TaskResponse updateTask(Integer id, TaskUpdateRequest taskUpdateRequest) {
        UserEntity user = userRepository.findById(taskUpdateRequest.getAssignedUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));

        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));

        taskEntity.setTitle(taskUpdateRequest.getTitle());
        taskEntity.setDescription(taskUpdateRequest.getDescription());
        taskEntity.setStatus(taskUpdateRequest.getStatus() != null ? taskUpdateRequest.getStatus() : TaskStatus.TODO);
        taskEntity.setUser(user);

        return taskEntityToTaskResponse(taskRepository.save(taskEntity));
    }

    public void deleteTaskById(Integer id) {
        if(!taskRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        taskRepository.deleteById(id);
    }

    public TaskResponse updateTaskStatusById(Integer id, TaskStatusUpdate status) {
        TaskEntity taskEntity = taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        taskEntity.setStatus(status.getTaskStatus());
        return taskEntityToTaskResponse(taskRepository.save(taskEntity));
    }

    public List<TaskResponse> findByStatus(TaskStatus taskStatus) {
        List<TaskEntity> taskEntities = taskRepository.findByStatus(taskStatus);

        return taskEntities.stream()
                .map(this::taskEntityToTaskResponse)
                .toList();
    }

    public List<TaskResponse> findByTitle(String title) {
        List<TaskEntity> taskEntities = taskRepository.findByTitleContainingIgnoreCase(title);

        return taskEntities.stream()
                .map(this::taskEntityToTaskResponse)
                .toList();
    }

    public Page<TaskResponse> getAllTasksPage(Pageable pageable) {
        return taskRepository.findAll(pageable).map(this::taskEntityToTaskResponse);
    }
}
