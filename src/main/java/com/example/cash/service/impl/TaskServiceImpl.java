package com.example.cash.service.impl;

import com.example.cash.domain.exception.ResourceNotFoundEx;
import com.example.cash.domain.task.Status;
import com.example.cash.domain.task.Task;
import com.example.cash.repo.TaskRepo;
import com.example.cash.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;

    @Override
    @Transactional(readOnly = true)
    public Task getById(Long id) {
        return taskRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundEx("Task not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> getAllByUserId(Long userId) {
        return taskRepo.findAllByUserId(userId);
    }

    @Override
    @Transactional
    public Task update(Task task) {
        if (task.getStatus() == null) {
            task.setStatus(Status.TODO);
        }
        taskRepo.update(task);
        return task;
    }

    @Override
    @Transactional
    public Task create(Task task, Long userId) {
        task.setStatus(Status.TODO);
        taskRepo.create(task);
        taskRepo.assignToUserById(task.getId(), userId);
        return task;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        taskRepo.delete(id);
    }
}
