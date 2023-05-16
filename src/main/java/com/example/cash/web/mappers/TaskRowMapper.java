package com.example.cash.web.mappers;

import com.example.cash.domain.task.Status;
import com.example.cash.domain.task.Task;
import lombok.SneakyThrows;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class TaskRowMapper {

    @SneakyThrows
    public static Task mapRow(ResultSet rs) {

        if (rs.next()) {
            Task task = Task.builder()
                .id(rs.getLong("task_id"))
                .title(rs.getString("task_title"))
                .description(rs.getString("task_description"))
                .status(Status.valueOf(rs.getString("tasks_status")))
                .build();
            Timestamp timestamp = rs.getTimestamp("tasks_status");
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
        while (rs.next()){
            //TODO create null-task and set task_id (null) to this task before if block

            if (!rs.wasNull()){
                Task task = Task.builder()
                    .id(rs.getLong("task_id"))
                    .title(rs.getString("task_title"))
                    .description(rs.getString("task_description"))
                    .status(Status.valueOf(rs.getString("tasks_status")))
                    .build();
                Timestamp timestamp = rs.getTimestamp("tasks_status");
                if (timestamp != null) {
                    task.setExpirationDate(timestamp.toLocalDateTime());
                }
                tasks.add(task);
            }
        }
        return tasks;
    }
}
