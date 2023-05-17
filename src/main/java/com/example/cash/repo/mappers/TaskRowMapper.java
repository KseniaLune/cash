package com.example.cash.repo.mappers;

import com.example.cash.domain.task.Status;
import com.example.cash.domain.task.Task;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Log4j
public class TaskRowMapper {

    @SneakyThrows
    public static Task mapRow(ResultSet rs) {

        if (rs.next()) {
            Task task = Task.builder()
                .id(rs.getLong("task_id"))
                .title(rs.getString("task_title"))
                .description(rs.getString("task_description"))
                .status(Status.valueOf(rs.getString("task_status")))
                .build();
            Timestamp timestamp = rs.getTimestamp("task_expiration_date");
            if (timestamp != null) {
                task.setExpirationDate(timestamp.toLocalDateTime());
            }
            return task;
        }
        return null;
    }

    @SneakyThrows
    public static List<Task> mapManyRows(ResultSet rs) {
        List<Task> tasks = new ArrayList<>();
        while (rs.next()) {
            Task task = Task.builder()
                .id(rs.getLong("task_id"))
                .build();
            if (!rs.wasNull()) {
                task.setTitle(rs.getString("task_title"));
                task.setDescription(rs.getString("task_description"));
                task.setStatus(Status.valueOf(rs.getString("task_status")));
                Timestamp timestamp = rs.getTimestamp("task_expiration_date");
                if (timestamp != null) {
                    task.setExpirationDate(timestamp.toLocalDateTime());
                }
                tasks.add(task);
            }
        }
        return tasks;
    }
}