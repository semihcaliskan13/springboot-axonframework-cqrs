package com.cqrsaxon.practice.productservice.coreapi;

import com.cqrsaxon.practice.productservice.collection.Product;
import com.cqrsaxon.practice.shared.command.CancelProductReservationCommand;
import com.cqrsaxon.practice.shared.command.ReserveProductCommand;
import event.ProductReservationCancelledEvent;
import event.ProductReservedEvent;
import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class ProductAggregate {
    @AggregateIdentifier
    private String productId;
    private String title;
    private BigDecimal price;
    private Integer quantity;

    public ProductAggregate() {
    }

    @CommandHandler(routingKey = "CreateProductCommand")
    public ProductAggregate(CreateProductCommand createProductCommand) {
        if (createProductCommand.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price cannot be less or equal to zero");
        }
        if (createProductCommand.getTitle() == null || createProductCommand.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }

        ProductCreatedEvent event = new ProductCreatedEvent();
        BeanUtils.copyProperties(createProductCommand, event);
        apply(event);
    }

    @CommandHandler
    public void handle(ReserveProductCommand reserveProductCommand) {
        if (quantity < reserveProductCommand.getQuantity()) {
            throw new IllegalArgumentException("Insufficient number of items in stock");
        }
        ProductReservedEvent event = ProductReservedEvent.builder()
                .userId(reserveProductCommand.getUserId())
                .productId(reserveProductCommand.getProductId())
                .orderId(reserveProductCommand.getOrderId())
                .quantity(reserveProductCommand.getQuantity())
                .build();

        apply(event);
    }

    @CommandHandler
    public void handle(CancelProductReservationCommand command) {
        ProductReservationCancelledEvent event = ProductReservationCancelledEvent.builder()
                .productId(command.getProductId())
                .reason(command.getReason())
                .orderId(command.getOrderId())
                .quantity(command.getQuantity())
                .userId(command.getUserId())
                .build();

        apply(event);
    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent event) {
        this.productId = event.getProductId();
        this.price = event.getPrice();
        this.title = event.getTitle();
        this.quantity = event.getQuantity();
    }

    @EventSourcingHandler
    public void on(ProductReservedEvent event) {
        this.quantity = event.getQuantity();
    }

    @EventSourcingHandler
    public void on(ProductReservationCancelledEvent event) {
        this.quantity += event.getQuantity();
    }
}
