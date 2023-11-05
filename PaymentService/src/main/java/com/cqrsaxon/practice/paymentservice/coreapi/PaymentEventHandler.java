package com.cqrsaxon.practice.paymentservice.coreapi;

import com.cqrsaxon.practice.paymentservice.collection.Payment;
import com.cqrsaxon.practice.paymentservice.repository.PaymentRepository;
import event.PaymentProcessedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventHandler {

    private final PaymentRepository repository;

    public PaymentEventHandler(PaymentRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(PaymentProcessedEvent event) {
        Payment payment = new Payment(event.getPaymentId(), event.getOrderId());
        BeanUtils.copyProperties(event,payment);
        repository.save(payment);

    }
}
