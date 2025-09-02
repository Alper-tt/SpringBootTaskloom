package com.taskloom.model.request;

import com.taskloom.model.TaskStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskStatusUpdate{
    @NotNull
    TaskStatus taskStatus;
}
