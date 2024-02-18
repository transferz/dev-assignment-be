package com.transferz.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class GenericSuccessResponse {
    private String message;
    private Object details;
}
