package event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor(force = true)
@Builder
public class ProductReservationCancelledEvent {
    private final String productId;
    private final String orderId;
    private final String userId;
    private final String reason;
    private final int quantity;
}
