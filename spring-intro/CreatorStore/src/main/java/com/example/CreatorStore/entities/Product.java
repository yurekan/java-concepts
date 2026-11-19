package com.example.CreatorStore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Product entity representing items available in the store")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Auto-generated database ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "Product name is required")
    @Column(nullable = false)
    @Schema(description = "Name of the product", example = "Mechanical Keyboard")
    private String name;

    @Schema(description = "Detailed product description", example = "RGB mechanical keyboard with blue switches")
    private String description;

    @Schema(description = "Product category", example = "Electronics")
    private String category;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(precision = 10, scale = 2, nullable = false)
    @Schema(description = "Unit price of the product", example = "99.99")
    private BigDecimal price;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock cannot be less than 0")
    @Column(name = "stock_quantity", nullable = false)
    @Schema(description = "Available units in stock", example = "50")
    private Integer stockQuantity;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    @Schema(hidden = true)
    private List<OrderItem> orderItems;
}
