package com.cqrsaxon.practice.orderservice.coreapi;

import com.cqrsaxon.practice.shared.command.ProcessPaymentCommand;
import com.cqrsaxon.practice.shared.command.ReserveProductCommand;
import event.ProductReservedEvent;
import lombok.extern.slf4j.Slf4j;
import model.User;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import query.FetchUserPaymentDetailsQuery;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Saga
@Slf4j
public class OrderSaga {

    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private QueryGateway queryGateway;

    public OrderSaga() {
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent) {
        //process product stock operations.
        ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .productId(orderCreatedEvent.getProductId())
                .quantity(orderCreatedEvent.getQuantity())
                .userId(orderCreatedEvent.getUserId())
                .build();
        log.info(String.format("Order created event handled for orderId: %s and productId: %s",
                orderCreatedEvent.getOrderId(), orderCreatedEvent.getProductId()));
        SagaLifecycle.associateWith("orderId", orderCreatedEvent.getOrderId());
        commandGateway.send(reserveProductCommand, (commandMessage, commandResultMessage) -> {
            if (commandResultMessage.isExceptional()) {
                //start a compensating transaction
            }
        });

    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent event) {
        //process user payment operations.
        log.info(String.format("ProductReservedEvent is called for productId: %s and orderId: %s", event.getProductId(), event.getOrderId()));
        FetchUserPaymentDetailsQuery query = FetchUserPaymentDetailsQuery.builder().userId(event.getUserId()).build();
        User user = null;

        try {
            user = queryGateway.query(query, ResponseTypes.instanceOf(User.class)).join();
        } catch (Exception exception) {
            log.error(exception.getMessage());
            //start compensating transaction.
            return;
        }
        if (user == null) {
            //start compensating transaction.
        }
        log.info(String.format("Fetched user: %s ", user.getFirstName() + " " + user.getLastName()));

        ProcessPaymentCommand processPaymentCommand = ProcessPaymentCommand.builder()
                .orderId(event.getOrderId())
                .paymentId(UUID.randomUUID().toString())
                .paymentDetails(user.getPaymentDetails())
                .build();
        String result = null;
        try {
            result = commandGateway.sendAndWait(processPaymentCommand, 10, TimeUnit.SECONDS);
        } catch (Exception e) {
            //start compensating transaction.
            log.error(e.getMessage());
        }
        if (result == null) {
            //start compensating transaction.
        }
    }


}
