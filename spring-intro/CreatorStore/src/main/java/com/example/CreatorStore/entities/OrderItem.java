package com.example.CreatorStore.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "OrderItem entity representing an item within an order")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Order item ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "price_at_purchase", precision = 10, scale = 2, nullable = false)
    @Schema(description = "Price per unit at purchase time", example = "99.99")
    private BigDecimal priceAtPurchase;

    @Column(nullable = false)
    @Schema(description = "Quantity purchased", example = "2")
    private Integer quantity;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @Schema(hidden = true)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    @Schema(description = "Purchased product details")
    private Product product;
}
