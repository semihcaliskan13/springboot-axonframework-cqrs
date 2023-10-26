package com.cqrsaxon.practice.productservice.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateProductRequest {
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
