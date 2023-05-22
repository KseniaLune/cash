package com.example.cash.web.controller;

import com.example.cash.domain.task.Task;
import com.example.cash.service.TaskService;
import com.example.cash.web.dto.task.TaskDto;
import com.example.cash.web.dto.validation.OnUpdate;
import com.example.cash.web.mappers.TaskMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Validated
@Tag(name = "Task Controller", description = "Task API")
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @GetMapping("/test")
    public ResponseEntity test() {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body("ok, task controller wooooorks!");
    }

    @PutMapping
    public TaskDto update(@Validated(OnUpdate.class) @RequestBody TaskDto dto) {
        Task taskUpdated = taskService.update(taskMapper.toEntity(dto));
        return taskMapper.toDto(taskUpdated);
    }

    @GetMapping("/{id}")
    public TaskDto getById(@PathVariable Long id) {
        Task task = taskService.getById(id);
        return taskMapper.toDto(task);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        taskService.delete(id);
    }

}
