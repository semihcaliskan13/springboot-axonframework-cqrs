package com.cqrsaxon.practice.productservice.dto.request;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateProductRequest {
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
