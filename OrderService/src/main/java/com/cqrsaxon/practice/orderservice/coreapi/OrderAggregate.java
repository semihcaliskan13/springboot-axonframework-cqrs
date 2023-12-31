package com.cqrsaxon.practice.orderservice.coreapi;

import com.cqrsaxon.practice.orderservice.enums.OrderStatus;
import event.OrderRejectedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class OrderAggregate {
    @AggregateIdentifier
    public String orderId;
    private String userId;
    private String productId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;

    public OrderAggregate() {

    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);
        apply(orderCreatedEvent);
    }

    @CommandHandler
    public void handle(ApproveOrderCommand command) {
        OrderApprovedEvent event = new OrderApprovedEvent(command.getOrderId());
        apply(event);
    }

    @CommandHandler
    public void handle(RejectOrderCommand command) {
        OrderRejectedEvent event = OrderRejectedEvent.builder()
                .reason(command.getReason())
                .orderId(command.getOrderId())
                .build();

        apply(event);
    }

    @EventSourcingHandler
    public void on(OrderRejectedEvent event){
        this.orderStatus=event.getOrderStatus();
    }

    @EventSourcingHandler
    public void on(OrderApprovedEvent event) {
        this.orderStatus = event.getOrderStatus();
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.orderStatus = event.getOrderStatus();
        this.productId = event.getProductId();
        this.userId = event.getUserId();
        this.addressId = event.getAddressId();
        this.quantity = event.getQuantity();
    }
}
