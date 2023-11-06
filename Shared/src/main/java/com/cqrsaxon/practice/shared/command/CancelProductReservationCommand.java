package com.cqrsaxon.practice.shared.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CancelProductReservationCommand {
    @TargetAggregateIdentifier
    private final String productId;
    private final int quantity;
    private final String orderId;
    private final String userId;
    private final String reason;

}
