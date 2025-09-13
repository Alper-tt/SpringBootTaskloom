package com.taskloom.repository;

import com.taskloom.entity.TaskEntity;
import com.taskloom.model.TaskStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Integer> {
    List<TaskEntity> findByTitleContainingIgnoreCase(String title);
    List<TaskEntity> findByStatus(TaskStatus status);

    @Query("SELECT t FROM TaskEntity t WHERE (:query is null) OR t.title ILIKE %:query% OR t.description ilike %:query%")
    Page<TaskEntity> findByTitleOrStatus(String query, Pageable pageable);
}
