package com.example.CreatorStore.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Standard API error response payload")
public class ErrorResponse {

    @Schema(description = "Timestamp when the error occurred", example = "2026-11-19T10:15:30")
    private LocalDateTime timestamp;

    @Schema(description = "HTTP status code", example = "404")
    private int status;

    @Schema(description = "HTTP status reason phrase", example = "Not Found")
    private String error;

    @Schema(description = "Detailed error message", example = "Product not found with id 1")
    private String message;

    @Schema(description = "Request path that triggered the error", example = "/api/products/1")
    private String path;

    @Schema(description = "Field-level validation error details, if applicable")
    private Map<String, String> validationErrors;
}
