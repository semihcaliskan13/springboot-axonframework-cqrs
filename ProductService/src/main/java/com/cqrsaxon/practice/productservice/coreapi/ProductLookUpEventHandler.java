package com.cqrsaxon.practice.productservice.coreapi;

import com.cqrsaxon.practice.productservice.collection.ProductLookUpCollection;
import com.cqrsaxon.practice.productservice.repository.ProductLookUpRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductLookUpEventHandler {

    private final ProductLookUpRepository repository;

    public ProductLookUpEventHandler(ProductLookUpRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) {
        ProductLookUpCollection productLookUp = new ProductLookUpCollection(event.getProductId(), event.getTitle());
        repository.save(productLookUp);

    }

}
