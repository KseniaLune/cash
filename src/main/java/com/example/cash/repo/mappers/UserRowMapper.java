package com.example.cash.repo.mappers;

import com.example.cash.domain.task.Task;
import com.example.cash.domain.user.Role;
import com.example.cash.domain.user.User;
import com.example.cash.repo.mappers.TaskRowMapper;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class UserRowMapper {
//    @SneakyThrows
//    public static User mapRow(ResultSet rs) {
//        Set<Role> roles = new HashSet<>();
//        while (rs.next()){
//            roles.add(Role.valueOf(rs.getString("user_role_role")));
//        }
//        rs.beforeFirst();
//        List<Task> tasks = TaskRowMapper.mapManyRows(rs);
//        rs.beforeFirst();
//
//        if (rs.next()){
//            User user = User.builder()
//                .id(rs.getLong("user_id"))
//                .name(rs.getString("user_name"))
//                .username(rs.getString("user_username"))
//                .password(rs.getString("user_password"))
//                .roles(roles)
//                .tasks(tasks)
//                .build();
//            return user;
//        }
//        return null;
//    }
//}

    @SneakyThrows
    public static User mapRow(ResultSet resultSet) {
        Set<Role> roles = new HashSet<>();
        while (resultSet.next()) {
            roles.add(Role.valueOf(resultSet.getString("user_role_role")));
        }
        resultSet.beforeFirst();
        List<Task> tasks = TaskRowMapper.mapManyRows(resultSet);
        resultSet.beforeFirst();
        if (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getLong("user_id"));
            user.setName(resultSet.getString("user_name"));
            user.setUsername(resultSet.getString("user_username"));
            user.setPassword(resultSet.getString("user_password"));
            user.setRoles(roles);
            user.setTasks(tasks);
            return user;
        }
        return null;
    }

}
