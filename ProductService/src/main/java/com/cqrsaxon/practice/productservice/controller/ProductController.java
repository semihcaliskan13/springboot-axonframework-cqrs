package com.cqrsaxon.practice.productservice.controller;

import com.cqrsaxon.practice.productservice.coreapi.CreateProductCommand;
import com.cqrsaxon.practice.productservice.dto.request.CreateProductRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final CommandGateway commandGateway;

    public ProductController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @GetMapping
    public Map<String, String> helloProduct() {
        Map<String, String> products = new HashMap<>();
        products.put("id","1");
        products.put("name", "water");
        products.put("price","4$");

        return products;
    }

    @PostMapping
    public String createProduct(@RequestBody CreateProductRequest request) throws ExecutionException, InterruptedException {
        CreateProductCommand productCommand =CreateProductCommand.builder()
                .price(request.getPrice())
                .title(request.getTitle())
                .productId(UUID.randomUUID().toString())
                .quantity(request.getQuantity()).build();

         CompletableFuture<String> returnValue = commandGateway.send(productCommand);
        return returnValue.get();
    }
}
