package com.cqrsaxon.practice.orderservice.coreapi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class RejectOrderCommand {

    @TargetAggregateIdentifier
    private final String orderId;
    private final String reason;
}
