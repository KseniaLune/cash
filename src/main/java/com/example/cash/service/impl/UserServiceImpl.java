package com.example.cash.service.impl;

import com.example.cash.domain.exception.ResourceNotFoundEx;
import com.example.cash.domain.user.Role;
import com.example.cash.domain.user.User;
import com.example.cash.repo.UserRepo;
import com.example.cash.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::getById", key = "#id")
    public User getById(Long id) {
        return userRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundEx("User not found."));
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::getByUsername", key = "#username")
    public User getByUsername(String username) {
        return userRepo.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundEx("User not found."));
    }

    @Override
    @Transactional
    @Caching(put = {
        @CachePut(value ="UserService::getById", key = "#user.id"),
        @CachePut(value ="UserService::getByUsername", key = "#user.username")
    })
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.update(user);
        return user;
    }

    @Override
    @Transactional
    @Caching(cacheable = {
        @Cacheable(value ="UserService::getById", key = "#user.id"),
        @Cacheable(value ="UserService::getByUsername", key = "#user.username")
    })
    public User create(User user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("User already exist.");
        }
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException("Passwords do not match.");
        }
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } catch (Exception e){
        }
            userRepo.create(user);

        Set<Role> roles = Set.of(Role.ROLE_USER);
        userRepo.insertUserRole(user.getId(), Role.ROLE_USER);
        user.setRoles(roles);
        return user;
    }

    @Override
    @Transactional
    @Cacheable(value = "UserService::isTaskOwner", key = "#userId" + '.' + "#taskId")
    public boolean isTaskOwner(Long userId, Long taskId) {
        return userRepo.isTaskOwner(userId, taskId);
    }

    @Override
    @Transactional
    @CacheEvict(value = "UserService::getById", key = "#id")
    public void delete(Long id) {
        userRepo.delete(id);
    }
}
