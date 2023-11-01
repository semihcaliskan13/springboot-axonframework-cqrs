package com.cqrsaxon.practice.productservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateProductRequest {
    @NotBlank(message = "Product title is required!")
    private String title;
    @Min(value = 1, message = "Price cannot be lower than one!")
    @Max(value = 1000, message = "Price cannot be upper than 1000!")
    @NotNull(message = "Price is required!")
    private BigDecimal price;
    @NotNull(message = "Quantity is required!")
    private Integer quantity;
}
