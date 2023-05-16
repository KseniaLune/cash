package com.example.cash.domain.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
    private Long id;
    private String title;
    private String description;
    private Status status;
    private LocalDateTime expirationDate;
}
