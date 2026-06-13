package kraynov.n.financialaccountingsystembackend.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record TransactionResponseTo(
        @JsonProperty("id") String id,
        @JsonProperty("description") String description,
        @JsonProperty("senderNodeId") String senderNodeId,
        @JsonProperty("receiverNodeId") String receiverNodeId,
        @JsonProperty("senderNodeName") String senderNodeName,
        @JsonProperty("receiverNodeName") String receiverNodeName,
        @JsonProperty("senderAmount") BigDecimal senderAmount,
        @JsonProperty("receiverAmount") BigDecimal receiverAmount,
        @JsonProperty("senderCurrencyId") String senderCurrencyId,
        @JsonProperty("senderCurrencySymbol") String senderCurrencySymbol,
        @JsonProperty("receiverCurrencyId") String receiverCurrencyId,
        @JsonProperty("receiverCurrencySymbol") String receiverCurrencySymbol,
        @JsonProperty("date") LocalDate date,
        @JsonProperty("cancelled") boolean isCancelled,
        @JsonProperty("userId") String userId,
        @JsonProperty("fromExternal") boolean isFromExternal,
        @JsonProperty("toExternal") boolean isToExternal,
        @JsonProperty("order") int order
) {
}
