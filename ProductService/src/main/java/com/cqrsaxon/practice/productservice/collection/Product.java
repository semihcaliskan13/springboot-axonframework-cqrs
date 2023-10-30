package com.cqrsaxon.practice.productservice.collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "product")
public class Product {

    @Id
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;
}
