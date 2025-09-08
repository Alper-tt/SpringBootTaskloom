package com.taskloom.service;

import com.taskloom.entity.UserEntity;
import com.taskloom.model.request.UserCreateRequest;
import com.taskloom.model.request.UserUpdateRequest;
import com.taskloom.model.response.TaskResponse;
import com.taskloom.model.response.UserResponse;
import com.taskloom.repository.UserRepository;
import com.taskloom.utils.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService extends Util {
    private final UserRepository userRepository;

    public List<UserResponse> findAll(){
        List<UserEntity> userEntities = userRepository.findAll();
        if(userEntities.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "User not found");
        }

        return userEntities.stream()
                .map(this::userEntityToUserResponse)
                .toList();
    }

    public UserResponse findById(Integer id){
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return userEntityToUserResponse(userEntity);
    }

    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        UserEntity userEntity = UserEntity.builder()
                .username(userCreateRequest.getUsername())
                .mail(userCreateRequest.getMail())
                .build();

        userRepository.save(userEntity);
        return userEntityToUserResponse(userRepository.save(userEntity));
    }

    public void deleteUserById(Integer id){
        if(!userRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        userRepository.deleteById(id);
    }

    public UserResponse findByUsername(String username) {
        if(!userRepository.existsByUsername(username)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        UserEntity userEntity = userRepository.findByUsername(username);
        return userEntityToUserResponse(userEntity);
    }


    public UserResponse findByMail(String mail) {
        if(userRepository.existsByUsername(mail)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        UserEntity userEntity = userRepository.findByMail(mail);
        return userEntityToUserResponse(userEntity);
    }

    public UserResponse updateUserById(Integer id, UserUpdateRequest userUpdateRequest) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userEntity.setUsername(userUpdateRequest.getUsername());
        userEntity.setMail(userUpdateRequest.getMail());

        return userEntityToUserResponse(userRepository.save(userEntity));
    }

    public List<TaskResponse> getUserTasksById(Integer userId){
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        return userEntity.getUserTasks().stream()
                .map(this::taskEntityToTaskResponse)
                .toList();
    }
}
