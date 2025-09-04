package com.taskloom.repository;

import com.taskloom.entity.TaskEntity;
import com.taskloom.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
    List<TaskEntity> findByTitleContainingIgnoreCase(String title);
    List<TaskEntity> findByStatus(TaskStatus status);
}
