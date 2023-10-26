package com.cqrsaxon.practice.productservice.coreapi;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class ProductAggregate {
    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public ProductAggregate() {
    }

    @CommandHandler
    public ProductAggregate(CreateProductCommand createProductCommand){
        if (createProductCommand.getPrice().compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("Price cannot be less or equal to zero");
        }
        if (createProductCommand.getTitle() == null || createProductCommand.getTitle().isBlank()){
            throw new IllegalArgumentException("Title cannot be empty");
        }

        ProductCreatedEvent event = new ProductCreatedEvent();
        BeanUtils.copyProperties(createProductCommand,event);
        apply(event);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event){
        this.productId= event.getProductId();
        this.price=event.getPrice();
        this.title=event.getTitle();
        this.quantity=event.getQuantity();
    }
}
