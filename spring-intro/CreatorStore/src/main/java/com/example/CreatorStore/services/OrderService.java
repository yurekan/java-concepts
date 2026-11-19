package com.example.CreatorStore.services;

import com.example.CreatorStore.dto.OrderItemRequest;
import com.example.CreatorStore.dto.OrderRequest;
import com.example.CreatorStore.entities.Order;
import com.example.CreatorStore.entities.OrderItem;
import com.example.CreatorStore.entities.Product;
import com.example.CreatorStore.exceptions.InsufficientStockException;
import com.example.CreatorStore.exceptions.ResourceNotFoundException;
import com.example.CreatorStore.repositories.OrderRepository;
import com.example.CreatorStore.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Order createOrder(OrderRequest orderRequest) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> orderItemList = new ArrayList<>();

        Order order = new Order();
        order.setCustomerName(orderRequest.getCustomerName());
        order.setCustomerEmail(orderRequest.getCustomerEmail());
        order.setStatus("CONFIRMED");

        for (OrderItemRequest orderItemRequest : orderRequest.getOrderItems()) {
            // Check if Product is present
            Product product = productRepository.findById(orderItemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Product not found with id " + orderItemRequest.getProductId()));

            // Check for product stock
            if (product.getStockQuantity() < orderItemRequest.getQuantity()) {
                throw new InsufficientStockException(
                        "Insufficient stock for product '" + product.getName() +
                                "' (ID: " + product.getId() + "). Available: " +
                                product.getStockQuantity() + ", requested: " + orderItemRequest.getQuantity());
            }

            // Calculate total price
            BigDecimal priceOfItem = product.getPrice()
                    .multiply(BigDecimal.valueOf(orderItemRequest.getQuantity()));

            totalPrice = totalPrice.add(priceOfItem);

            // Update the product stock quantity
            product.setStockQuantity(product.getStockQuantity() - orderItemRequest.getQuantity());
            productRepository.save(product);

            // Build OrderItem
            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(orderItemRequest.getQuantity())
                    .priceAtPurchase(product.getPrice())
                    .build();

            orderItemList.add(orderItem);
        }

        order.setTotalPrice(totalPrice);
        order.setOrderItems(orderItemList);

        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
    }
}
