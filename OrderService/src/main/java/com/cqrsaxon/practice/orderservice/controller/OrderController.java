package com.cqrsaxon.practice.orderservice.controller;

import com.cqrsaxon.practice.orderservice.coreapi.CreateOrderCommand;
import com.cqrsaxon.practice.orderservice.dto.request.CreateOrderRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CommandGateway commandGateway;

    public OrderController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createOrder(@RequestBody CreateOrderRequest request) {
        CreateOrderCommand orderCommand = CreateOrderCommand.builder()
                .orderId(UUID.randomUUID().toString())
                .userId("27b95829-4f3f-4ddf-8983-151ba010e35b")
                .productId(request.getProductId())
                .addressId(request.getAddressId())
                .quantity(request.getQuantity())
                .build();
        CompletableFuture<String> returnValue = commandGateway.send(orderCommand);
        return returnValue.join();
    }
}
