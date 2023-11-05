package com.cqrsaxon.practice.userservice.coreapi;

import model.PaymentDetails;
import model.User;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import query.FetchUserPaymentDetailsQuery;

@Component
public class PaymentQueryHandler {

    @QueryHandler
    public User handle(FetchUserPaymentDetailsQuery query){
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .cardNumber("123Card")
                .cvv("123")
                .name("ELON KARAMAZOV")
                .validUntilMonth(12)
                .validUntilYear(2030)
                .build();

        User user = User.builder()
                .firstName("ELON")
                .lastName("KARAMAZOV")
                .userId(query.getUserId())
                .paymentDetails(paymentDetails)
                .build();

        return user;

    }
}
