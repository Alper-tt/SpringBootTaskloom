package com.taskloom.utils;

import com.taskloom.entity.TaskEntity;
import com.taskloom.entity.UserEntity;
import com.taskloom.model.response.TaskResponse;
import com.taskloom.model.response.UserResponse;

public class Util {

    public TaskResponse taskEntityToTaskResponse(TaskEntity taskEntity){
        return new TaskResponse(taskEntity.getId(),
                taskEntity.getTitle(),
                taskEntity.getDescription(),
                taskEntity.getStatus(),
                taskEntity.getCreatedAt(),
                taskEntity.getUpdatedAt());
    }

    public UserResponse userEntityToUserResponse(UserEntity user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getMail()
        );
    }
}
