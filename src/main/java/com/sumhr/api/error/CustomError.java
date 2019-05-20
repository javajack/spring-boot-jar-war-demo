package com.sumhr.api.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class CustomError {

    private final CustomErrorType tenoErrorType;
    private final String errorMessage;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private final Date timestamp;
    private final List<CustomFieldError> fieldErrors;

}
