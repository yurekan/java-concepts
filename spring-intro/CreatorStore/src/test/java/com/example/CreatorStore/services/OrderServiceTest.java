package com.example.CreatorStore.services;

import com.example.CreatorStore.dto.OrderItemRequest;
import com.example.CreatorStore.dto.OrderRequest;
import com.example.CreatorStore.entities.Order;
import com.example.CreatorStore.entities.Product;
import com.example.CreatorStore.exceptions.InsufficientStockException;
import com.example.CreatorStore.exceptions.ResourceNotFoundException;
import com.example.CreatorStore.repositories.OrderRepository;
import com.example.CreatorStore.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = Product.builder()
                .id(1L)
                .name("Wireless Mouse")
                .price(new BigDecimal("49.99"))
                .stockQuantity(10)
                .build();
    }

    @Test
    void createOrder_whenValid_shouldDeductStockAndSaveOrder() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order o = invocation.getArgument(0);
            o.setId(100L);
            return o;
        });

        OrderRequest request = OrderRequest.builder()
                .customerName("Jane Doe")
                .customerEmail("jane@example.com")
                .orderItems(List.of(
                        OrderItemRequest.builder()
                                .productId(1L)
                                .quantity(2)
                                .build()
                ))
                .build();

        Order createdOrder = orderService.createOrder(request);

        assertThat(createdOrder).isNotNull();
        assertThat(createdOrder.getCustomerName()).isEqualTo("Jane Doe");
        assertThat(createdOrder.getCustomerEmail()).isEqualTo("jane@example.com");
        assertThat(createdOrder.getStatus()).isEqualTo("CONFIRMED");
        assertThat(createdOrder.getTotalPrice()).isEqualByComparingTo(new BigDecimal("99.98"));
        assertThat(createdOrder.getOrderItems()).hasSize(1);
        assertThat(sampleProduct.getStockQuantity()).isEqualTo(8);

        verify(productRepository).save(sampleProduct);
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void createOrder_whenProductNotFound_shouldThrowResourceNotFoundException() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        OrderRequest request = OrderRequest.builder()
                .customerName("Jane Doe")
                .customerEmail("jane@example.com")
                .orderItems(List.of(
                        OrderItemRequest.builder()
                                .productId(99L)
                                .quantity(1)
                                .build()
                ))
                .build();

        assertThatThrownBy(() -> orderService.createOrder(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Product not found with id 99");

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void createOrder_whenInsufficientStock_shouldThrowInsufficientStockException() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        OrderRequest request = OrderRequest.builder()
                .customerName("Jane Doe")
                .customerEmail("jane@example.com")
                .orderItems(List.of(
                        OrderItemRequest.builder()
                                .productId(1L)
                                .quantity(20) // Only 10 available
                                .build()
                ))
                .build();

        assertThatThrownBy(() -> orderService.createOrder(request))
                .isInstanceOf(InsufficientStockException.class)
                .hasMessageContaining("Insufficient stock for product 'Wireless Mouse'");

        verify(orderRepository, never()).save(any(Order.class));
    }

    @Test
    void getOrderById_whenFound_shouldReturnOrder() {
        Order order = Order.builder()
                .id(1L)
                .customerName("John Doe")
                .customerEmail("john@example.com")
                .status("CONFIRMED")
                .totalPrice(new BigDecimal("50.00"))
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order found = orderService.getOrderById(1L);

        assertThat(found).isNotNull();
        assertThat(found.getCustomerName()).isEqualTo("John Doe");
    }

    @Test
    void getOrderById_whenNotFound_shouldThrowResourceNotFoundException() {
        when(orderRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> orderService.getOrderById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Order not found with id 99");
    }
}
