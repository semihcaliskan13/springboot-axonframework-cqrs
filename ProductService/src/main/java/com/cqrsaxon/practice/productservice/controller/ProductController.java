package com.cqrsaxon.practice.productservice.controller;

import com.cqrsaxon.practice.productservice.coreapi.CreateProductCommand;
import com.cqrsaxon.practice.productservice.coreapi.GetAllProductsQuery;
import com.cqrsaxon.practice.productservice.dto.request.CreateProductRequest;
import com.cqrsaxon.practice.productservice.dto.response.ProductGetAllResponseWrapper;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public ProductController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @GetMapping("/hello")
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

    @GetMapping
    public ProductGetAllResponseWrapper getAllProducts() throws ExecutionException, InterruptedException {
        GetAllProductsQuery query = new GetAllProductsQuery();
        return queryGateway.query(query, ResponseTypes.instanceOf(ProductGetAllResponseWrapper.class)).join();
    }
}
