package query;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FetchUserPaymentDetailsQuery {
    private String userId;

}
