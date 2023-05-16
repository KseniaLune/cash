package com.example.cash.service.impl;

import com.example.cash.domain.exception.ResourceNotFoundEx;
import com.example.cash.domain.user.Role;
import com.example.cash.domain.user.User;
import com.example.cash.repo.UserRepo;
import com.example.cash.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    public void setPasswordEncoder(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundEx("User not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userRepo.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundEx("User not found."));
    }

    @Override
    @Transactional
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.update(user);
        return user;
    }

    @Override
    @Transactional
    public User create(User user) {
        if (userRepo.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("User already exist.");
        }
        if (!user.getPassword().equals(user.getPasswordConfirmation())){
            throw new IllegalStateException("Passwords do not match.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.create(user);
        Set<Role> roles = Set.of(Role.ROLE_USER);
        userRepo.insertUserRole(user.getId(), Role.ROLE_USER);
        user.setRoles(roles);
        return user;
    }

    @Override
    @Transactional
    public boolean isTaskOwner(Long userId, Long taskId) {
        return userRepo.isTaskOwner(userId, taskId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        userRepo.delete(id);
    }
}
