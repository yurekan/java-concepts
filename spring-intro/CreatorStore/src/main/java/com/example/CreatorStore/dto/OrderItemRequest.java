package com.example.CreatorStore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Item and quantity details for an order")
public class OrderItemRequest {

    @NotNull(message = "Product id is required")
    @Schema(description = "ID of the product to order", example = "1")
    private Long productId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    @Schema(description = "Quantity of the product to order (must be at least 1)", example = "2")
    private Integer quantity;
}
