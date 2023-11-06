package event;

import com.cqrsaxon.practice.orderservice.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Builder
public class OrderRejectedEvent {
    private final String orderId;
    private final String reason;
    private final OrderStatus orderStatus = OrderStatus.REJECTED;
}
