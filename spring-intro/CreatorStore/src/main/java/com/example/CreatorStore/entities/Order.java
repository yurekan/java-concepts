package com.example.CreatorStore.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Order entity representing customer purchases")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Auto-generated order ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(name = "customer_name", nullable = false)
    @Schema(description = "Customer full name", example = "Jane Doe")
    private String customerName;

    @Column(name = "customer_email", nullable = false)
    @Schema(description = "Customer email address", example = "jane.doe@example.com")
    private String customerEmail;

    @Column(nullable = false)
    @Schema(description = "Current status of the order", example = "CONFIRMED")
    private String status;

    @Column(name = "total_price", precision = 12, scale = 2, nullable = false)
    @Schema(description = "Total cost of the order in USD", example = "199.98")
    private BigDecimal totalPrice;

    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Schema(description = "Items included in this order")
    private List<OrderItem> orderItems;

    @Column(name = "created_at")
    @Schema(description = "Order timestamp", example = "2026-11-19T10:15:30", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
