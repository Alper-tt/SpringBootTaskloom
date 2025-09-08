package com.taskloom.controller;

import com.taskloom.model.request.UserCreateRequest;
import com.taskloom.model.request.UserUpdateRequest;
import com.taskloom.model.response.TaskResponse;
import com.taskloom.model.response.UserResponse;
import com.taskloom.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userCreateRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUserById(@PathVariable Integer id, @RequestBody @Valid UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateUserById(id, userUpdateRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Integer id) {
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findByUsername(username));
    }

    @GetMapping("/mail/{mail}")
    public ResponseEntity<UserResponse> getUserByMail(@PathVariable String mail){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findByMail(mail));
    }

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<TaskResponse>> getUserTasksById(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserTasksById(id));
    }

}
