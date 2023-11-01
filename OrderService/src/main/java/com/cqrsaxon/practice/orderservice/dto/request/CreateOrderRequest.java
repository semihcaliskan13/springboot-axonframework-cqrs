package com.cqrsaxon.practice.orderservice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateOrderRequest {
    @NotNull(message = "ProductId is required")
    private String productId;
    private Integer quantity;
    private String addressId;
}
