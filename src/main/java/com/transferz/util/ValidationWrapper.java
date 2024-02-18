package com.transferz.util;

import com.transferz.dto.GenericSuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidationWrapper {

    private final GenericSuccessResponse successResponse;

    public GenericSuccessResponse mapSuccessResponse(Object object, String message) {
        successResponse.setMessage(message);
        successResponse.setDetails(object);
        return successResponse;
    }
}
