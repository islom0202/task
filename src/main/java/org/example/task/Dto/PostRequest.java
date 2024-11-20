package org.example.task.Dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PostRequest {
    @NotEmpty(message = "cannot be empty!")
    private String title;
    @NotEmpty(message = "cannot be empty!")
    private String content;
}
