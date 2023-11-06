package com.cqrsaxon.practice.orderservice.coreapi;

import com.cqrsaxon.practice.orderservice.collection.Order;
import com.cqrsaxon.practice.orderservice.repository.OrderRepository;
import event.OrderRejectedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;


@Component
public class OrderEventHandler {

    private final OrderRepository repository;

    public OrderEventHandler(OrderRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(OrderCreatedEvent event){
        Order order = new Order();
        BeanUtils.copyProperties(event,order);
        repository.save(order);
    }

    @EventHandler
    public void on(OrderApprovedEvent event){
        Order order = repository.findById(event.getOrderId()).orElseThrow();
        order.setOrderStatus(event.getOrderStatus());
        BeanUtils.copyProperties(event, order);
        repository.save(order);
    }

    @EventHandler
    public void on(OrderRejectedEvent event){
        Order order = repository.findById(event.getOrderId()).orElseThrow();
        order.setOrderStatus(event.getOrderStatus());
        //BeanUtils.copyProperties(event, order);
        repository.save(order);
    }
}
