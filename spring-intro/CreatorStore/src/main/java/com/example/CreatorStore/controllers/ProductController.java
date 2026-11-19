package com.example.CreatorStore.controllers;

import com.example.CreatorStore.entities.Product;
import com.example.CreatorStore.exceptions.ErrorResponse;
import com.example.CreatorStore.services.ProductService;
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
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Endpoints for managing products in the store catalog")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new product", description = "Adds a new product to the catalog with the provided details")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed for request body",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Product createProduct(@Valid @RequestBody Product product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product by ID", description = "Updates details of an existing product in the catalog")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product updated successfully"),
            @ApiResponse(responseCode = "400", description = "Validation failed for request body",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Product not found with the specified ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Product updateProduct(
            @Parameter(description = "ID of the product to update", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Returns a list of all products currently available in the catalog")
    @ApiResponse(responseCode = "200", description = "List of products retrieved successfully")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a product by ID", description = "Retrieves product details for the given product ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Product retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found with the specified ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Product getProductById(
            @Parameter(description = "ID of the product to retrieve", example = "1", required = true)
            @PathVariable Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a product by ID", description = "Removes a product from the catalog by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found with the specified ID",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public void deleteProduct(
            @Parameter(description = "ID of the product to delete", example = "1", required = true)
            @PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
