package com.example.CreatorStore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Request body payload for creating an order")
public class OrderRequest {

    @NotBlank(message = "Customer name is required")
    @Schema(description = "Customer full name", example = "Jane Doe")
    private String customerName;

    @NotBlank(message = "Customer email is required")
    @Email(message = "Customer email must be a valid email address")
    @Schema(description = "Customer contact email", example = "jane.doe@example.com")
    private String customerEmail;

    @Valid
    @NotEmpty(message = "Order must contain at least one item")
    @Schema(description = "List of order items to purchase")
    private List<OrderItemRequest> orderItems;
}
