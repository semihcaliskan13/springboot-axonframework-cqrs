package com.cqrsaxon.practice.productservice.coreapi;

import com.cqrsaxon.practice.productservice.collection.Product;
import com.cqrsaxon.practice.productservice.repository.ProductRepository;
import event.ProductReservedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@ProcessingGroup("product-group")
public class ProductEventHandler {

    private final ProductRepository productRepository;

    public ProductEventHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) throws RuntimeException {
        //query-side
        Product product = new Product();
        BeanUtils.copyProperties(productCreatedEvent, product);
        productRepository.save(product);
        //if (true) throw new RuntimeException("throwing an Exception!");
    }

    @EventHandler
    public void on(ProductReservedEvent event){
        Product product = productRepository.findByProductId(event.getProductId()).orElseThrow();
        product.setQuantity(product.getQuantity() - event.getQuantity());
        productRepository.save(product);
        log.info(String.format("ProductReservedEvent called for productId: %s and orderId: %S",event.getProductId(),event.getOrderId()));
    }


    @ExceptionHandler(resultType = IllegalStateException.class)
    public void handleException(IllegalStateException exception) throws RuntimeException {
        log.info(exception.getMessage());
        throw exception;
    }
}
