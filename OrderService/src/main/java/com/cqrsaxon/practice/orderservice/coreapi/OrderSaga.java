package com.cqrsaxon.practice.orderservice.coreapi;

import com.cqrsaxon.practice.shared.command.ReserveProductCommand;
import event.ProductReservedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

@Saga
@Slf4j
public class OrderSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    public OrderSaga() {
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent){
        //process product stock operations.
        ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .productId(orderCreatedEvent.getProductId())
                .quantity(orderCreatedEvent.getQuantity())
                .userId(orderCreatedEvent.getUserId())
                .build();
        log.info(String.format("Order created event handled for orderId: %s and productId: %s",
                orderCreatedEvent.getOrderId(),orderCreatedEvent.getProductId()));
        SagaLifecycle.associateWith("orderId", orderCreatedEvent.getOrderId());
        commandGateway.send(reserveProductCommand, (commandMessage, commandResultMessage) -> {
            if (commandResultMessage.isExceptional()){
                //start a compensating transaction
            }
        });

    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent event){
         //process user payment operations.
        log.info(String.format("ProductReservedEvent is called for productId: %s and orderId: %s",event.getProductId(),event.getOrderId()));
    }

}
