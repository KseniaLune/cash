package com.example.cash.web.controller;

import com.example.cash.domain.task.Status;
import com.example.cash.domain.task.Task;
import com.example.cash.service.TaskService;
import com.example.cash.web.dto.task.TaskDto;
import com.example.cash.web.mappers.TaskMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    TaskService taskService;
    @Mock
    TaskMapper taskMapper;
    @InjectMocks
    TaskController controller;

    @Test
    void update_ReturnValidResponseEntity() {
        //given
        Task task = Task.builder()
            .id(999L)
            .title("Task test title")
            .description("Task est description")
            .expirationDate(LocalDateTime.now())
            .status(Status.TODO)
            .build();
        TaskDto taskDto = TaskDto.builder()
            .id(999L)
            .title("TaskDto test title")
            .description("TaskDto test description")
            .status(Status.TODO)
            .expirationDate(LocalDateTime.now())
            .build();
        Mockito.doReturn(task).when(this.taskMapper).toEntity(taskDto);
        Mockito.doReturn(task).when(this.taskService).update(task);
        Mockito.doReturn(taskDto).when(this.taskMapper).toDto(task);

        //when
        var re = this.controller.update(taskDto);
        //then
        assertNotNull(re);
        assertEquals(HttpStatus.OK, re.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, re.getHeaders().getContentType());
        assertEquals(taskDto, re.getBody());
    }

    @Test
    void getById_ReturnsValidResponseEntity() {
        //given
        Task task = Task.builder()
            .id(999L)
            .title("Test title")
            .description("Test description")
            .expirationDate(LocalDateTime.now())
            .status(Status.TODO)
            .build();
        Mockito.doReturn(task).when(this.taskService).getById(999L);

        TaskDto taskDto = new TaskDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setStatus(task.getStatus());
        taskDto.setExpirationDate(task.getExpirationDate());
        Mockito.doReturn(taskDto).when(this.taskMapper).toDto(task);

        //when
        var re = this.controller.getById(999L);
        //then
        assertNotNull(re);
        assertEquals(HttpStatus.OK, re.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, re.getHeaders().getContentType());
        assertEquals(taskDto, re.getBody());
    }

    @Test
    void deleteById_ReturnsValidResponseEntity() {
        //given
        Mockito.doReturn("Task is deleting").when(this.taskService).delete(999L);
        //when
        var re = this.controller.deleteById(999L);
        //then
        assertNotNull(re);
        assertEquals(HttpStatus.OK, re.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, re.getHeaders().getContentType());
        assertEquals("Task is deleting", re.getBody());
    }
}