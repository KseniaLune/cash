package com.example.cash.web.controller;

import com.example.cash.domain.task.Task;
import com.example.cash.domain.user.User;
import com.example.cash.service.TaskService;
import com.example.cash.service.UserService;
import com.example.cash.web.dto.task.TaskDto;
import com.example.cash.web.dto.user.UserDto;
import com.example.cash.web.dto.validation.OnCreate;
import com.example.cash.web.dto.validation.OnUpdate;
import com.example.cash.web.mappers.TaskMapper;
import com.example.cash.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;
    private final TaskService taskservice;
    private final UserMapper userMapper;
    private final TaskMapper taskmapper;

    @GetMapping("/test")
    public ResponseEntity test(){
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("ok, user controller wooooorks!");
    }

    @PutMapping
    public UserDto update(@Validated(OnUpdate.class) @RequestBody UserDto dto) {
        User updatedUser = userService.update(userMapper.toEntity(dto));
        return userMapper.toDto(updatedUser);
    }

    @GetMapping("/{id}")
    public UserDto getUserBy(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUserBy(@PathVariable Long id) {
        userService.delete(id);
    }

    @GetMapping("/{id}/tasks")
    public List<TaskDto> getAllTasksByUser(@PathVariable Long id) {
        return taskmapper.toDto(taskservice.getAllByUserId(id));
    }

    @PostMapping("/{id}/tasks")
    public TaskDto createTask(@PathVariable Long id,
                              @Validated(OnCreate.class) @RequestBody TaskDto dto) {
        Task task = taskmapper.toEntity(dto);
        Task createdTask = taskservice.create(task, id);
        return taskmapper.toDto(createdTask);
    }


}
