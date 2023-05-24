package com.example.cash.service;

import com.example.cash.domain.task.Task;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TaskService {
    Task getById(Long id);

    List<Task> getAllByUserId(Long userId);

    Task update(Task task);

    Task create(Task task, Long userId);

    String delete(Long id);
}
