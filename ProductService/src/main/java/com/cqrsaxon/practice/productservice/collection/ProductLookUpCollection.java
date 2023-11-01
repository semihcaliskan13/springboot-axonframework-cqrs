package com.cqrsaxon.practice.productservice.collection;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "product-lookup")
public class ProductLookUpCollection {

    @Id
    private String productId;
    @Indexed(unique = true)
    private String title;
}
