package com.cqrsaxon.practice.orderservice.coreapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ApproveOrderCommand {
    @TargetAggregateIdentifier
    private final String orderId;

}
