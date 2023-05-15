package com.example.cash.repo;

import com.example.cash.domain.user.Role;
import com.example.cash.domain.user.User;
import org.mapstruct.Mapper;

import java.util.Optional;


public interface UserRepo {

   Optional<User> findById(Long id);

   Optional<User> findByUsername(String username);

   void update(User user);

   void create(User user);

   void insertUserRole(Long userId, Role role);

   boolean isTaskOwner(Long userId, Long taskId);

   void delete(Long id);

}
