package com.taskloom.repository;

import com.taskloom.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);

    UserEntity findByMail(String mail);
}
