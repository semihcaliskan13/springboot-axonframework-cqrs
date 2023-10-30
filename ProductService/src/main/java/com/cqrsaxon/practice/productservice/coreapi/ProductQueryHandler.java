package com.cqrsaxon.practice.productservice.coreapi;

import com.cqrsaxon.practice.productservice.dto.response.ProductGetAllResponse;
import com.cqrsaxon.practice.productservice.dto.response.ProductGetAllResponseWrapper;
import com.cqrsaxon.practice.productservice.repository.ProductRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductQueryHandler {

    private final ProductRepository productRepository;

    public ProductQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryHandler
    public ProductGetAllResponseWrapper getAllProducts(GetAllProductsQuery query) {
        List<ProductGetAllResponse> products = new ArrayList<>();
        productRepository.findAll().forEach(product -> {
            ProductGetAllResponse productResponse = new ProductGetAllResponse();
            BeanUtils.copyProperties(product,productResponse);
            products.add(productResponse);
        });
        return new ProductGetAllResponseWrapper(products);
    }
}
