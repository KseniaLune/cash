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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
@Tag(name = "User Controller", description = "User API")
public class UserController {

    private final UserService userService;
    private final TaskService taskservice;
    private final UserMapper userMapper;
    private final TaskMapper taskmapper;

    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("ok, user controller wooooorks!");
    }

    @PutMapping
    @Operation(summary = "Update user")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#dto.id)")
    public ResponseEntity <UserDto> update(@Validated(OnUpdate.class) @RequestBody UserDto dto) {
        User updatedUser = userService.update(userMapper.toEntity(dto));
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(userMapper.toDto(updatedUser));
    }

    @GetMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public ResponseEntity <UserDto> getUserBy(@PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(userMapper.toDto(user));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public ResponseEntity <String> deleteUserBy(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body("User was deleting.");
    }

    @GetMapping("/{id}/tasks")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public ResponseEntity <List<TaskDto>> getAllTasksByUser(@PathVariable Long id) {
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(taskmapper.toDto(taskservice.getAllByUserId(id)));
    }

    @PostMapping("/{id}/tasks")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public ResponseEntity <TaskDto> createTask(@PathVariable Long id,
                              @Validated(OnCreate.class) @RequestBody TaskDto dto) {
        Task task = taskmapper.toEntity(dto);
        Task createdTask = taskservice.create(task, id);
        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(taskmapper.toDto(createdTask));
    }


}
