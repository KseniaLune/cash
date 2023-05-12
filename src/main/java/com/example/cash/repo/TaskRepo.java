package com.example.cash.repo;

import com.example.cash.domain.task.Task;
import com.example.cash.domain.user.Role;
import com.example.cash.domain.user.User;

import java.util.List;
import java.util.Optional;

public interface TaskRepo {
    Optional<Task> findById(Long id);

    List<Task> findAllByUserId(Long userId);

    void assignToUserById(Long taskId, Long userId);

    void update(Task task);

    void create(Task task);

    void delete(Long id);
}
