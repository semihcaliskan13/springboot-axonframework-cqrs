package com.cqrsaxon.practice.productservice.coreapi;

import com.cqrsaxon.practice.productservice.collection.Product;
import com.cqrsaxon.practice.productservice.repository.ProductRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProductEventHandler {

    private final ProductRepository productRepository;

    public ProductEventHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent){
        Product product = new Product();
        BeanUtils.copyProperties(productCreatedEvent,product);
        productRepository.save(product);
    }
}
