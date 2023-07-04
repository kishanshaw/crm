package com.onetoone.mapping.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExceptionResponse {
    String message;
    String statusCode;
    String timestamp;
    String uri;
}
