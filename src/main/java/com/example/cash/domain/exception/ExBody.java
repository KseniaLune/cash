package com.example.cash.domain.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ExBody {

    private String message;
    private Map<String, String> errors;

    public ExBody(String message) {
        this.message = message;
    }
}
