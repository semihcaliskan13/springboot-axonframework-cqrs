package com.cqrsaxon.practice.orderservice.coreapi;

import com.cqrsaxon.practice.orderservice.enums.OrderStatus;
import lombok.Data;

@Data
public class OrderCreatedEvent {
    private String orderId;
    private String userId;
    private String productId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;
}
