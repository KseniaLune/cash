package com.example.cash.repo.impl;

import com.example.cash.domain.user.Role;
import com.example.cash.domain.user.User;
import com.example.cash.repo.UserRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepoImpl implements UserRepo {


    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void create(User user) {

    }

    @Override
    public void insertUserRole(Long userId, Role role) {

    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        return false;
    }

    @Override
    public void delete(Long id) {

    }
}
