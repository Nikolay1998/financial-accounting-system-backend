package kraynov.n.financialaccountingsystembackend.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequestTo(
        @JsonProperty("id") String id,
        @JsonProperty("description") String description,
        @JsonProperty("senderNodeId") String senderNodeId,
        @JsonProperty("receiverNodeId") String receiverNodeId,
        @JsonProperty("senderAmount") BigDecimal senderAmount,
        @JsonProperty("receiverAmount") BigDecimal receiverAmount,
        @JsonProperty("senderCurrencyId") String senderCurrencyId,
        @JsonProperty("receiverCurrencyId") String receiverCurrencyId,
        @JsonProperty("date") LocalDate date,
        @JsonProperty("cancelled") boolean isCancelled,
        @JsonProperty("userId") String userId
) {
}
