package com.sumhr.api.error;

import lombok.Data;

@Data
public class CustomFieldError {

    private final String field;
    private final String errorCode;
    private final String defaultMessage;

}
