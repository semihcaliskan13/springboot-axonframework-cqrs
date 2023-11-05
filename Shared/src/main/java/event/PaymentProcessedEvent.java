package event;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class PaymentProcessedEvent {
    private final String orderId;
    private final String paymentId;
}
