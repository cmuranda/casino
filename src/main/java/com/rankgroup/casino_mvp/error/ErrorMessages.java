package com.rankgroup.casino_mvp.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@AllArgsConstructor
@Data
public class ErrorMessages {
    private HttpStatus status;
    private List<String> errors;
}
