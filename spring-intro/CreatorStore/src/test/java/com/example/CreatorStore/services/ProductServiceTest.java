package com.example.CreatorStore.services;

import com.example.CreatorStore.entities.Product;
import com.example.CreatorStore.exceptions.ResourceNotFoundException;
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
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = Product.builder()
                .id(1L)
                .name("Keyboard")
                .description("Mechanical keyboard")
                .category("Electronics")
                .price(new BigDecimal("99.99"))
                .stockQuantity(10)
                .build();
    }

    @Test
    void createProduct_shouldSaveAndReturnProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

        Product created = productService.createProduct(sampleProduct);

        assertThat(created).isNotNull();
        assertThat(created.getName()).isEqualTo("Keyboard");
        verify(productRepository).save(sampleProduct);
    }

    @Test
    void getProductById_whenExists_shouldReturnProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        Product found = productService.getProductById(1L);

        assertThat(found.getId()).isEqualTo(1L);
        assertThat(found.getName()).isEqualTo("Keyboard");
    }

    @Test
    void getProductById_whenNotFound_shouldThrowResourceNotFoundException() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Product not found with id 99");
    }

    @Test
    void updateProduct_whenExists_shouldUpdateAndSave() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product updateRequest = Product.builder()
                .name("Updated Keyboard")
                .description("RGB Mechanical keyboard")
                .category("Gaming")
                .price(new BigDecimal("129.99"))
                .stockQuantity(15)
                .build();

        Product updated = productService.updateProduct(1L, updateRequest);

        assertThat(updated.getName()).isEqualTo("Updated Keyboard");
        assertThat(updated.getPrice()).isEqualTo(new BigDecimal("129.99"));
        assertThat(updated.getStockQuantity()).isEqualTo(15);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void deleteProduct_whenNotFound_shouldThrowResourceNotFoundException() {
        when(productRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> productService.deleteProduct(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Product not found with id 99");
    }

    @Test
    void deleteProduct_whenExists_shouldDelete() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void getAllProducts_shouldReturnList() {
        when(productRepository.findAll()).thenReturn(List.of(sampleProduct));

        List<Product> products = productService.getAllProducts();

        assertThat(products).hasSize(1);
        assertThat(products.get(0).getName()).isEqualTo("Keyboard");
    }
}
