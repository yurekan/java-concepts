package com.example.CreatorStore.controllers;

import com.example.CreatorStore.dto.OrderRequest;
import com.example.CreatorStore.entities.Order;
import com.example.CreatorStore.exceptions.ErrorResponse;
import com.example.CreatorStore.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Endpoints for creating and retrieving orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new order", description = "Places a new order for one or more items, verifying stock and calculating total cost")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Order created and confirmed successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed for request body",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "One or more requested products not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "Insufficient stock for one or more requested products",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Order createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }

    @GetMapping
    @Operation(summary = "Get all orders", description = "Retrieves a list of all placed orders")
    @ApiResponse(responseCode = "200", description = "List of orders retrieved successfully")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get an order by ID", description = "Retrieves order details for the given order ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Order retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Order not found with the specified ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Order getOrderById(
            @Parameter(description = "ID of the order to retrieve", example = "1", required = true)
            @PathVariable Long id) {
        return orderService.getOrderById(id);
    }
}
