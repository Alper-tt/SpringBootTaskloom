package com.taskloom.model.request;

import com.taskloom.model.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateRequest {
    @NotBlank
    @Size(min = 3, max = 255)
    private String title;

    @Size(min = 3, max = 255)
    private String description;

    private TaskStatus status;
}
