package com.cqrsaxon.practice.productservice.dto.response;

import lombok.Data;

import java.math.BigDecimal;



@Data
public class ProductGetAllResponse {

    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
