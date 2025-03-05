package kraynov.n.financialaccountingsystembackend.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequestTO {
    @JsonProperty("id")
    private final String id;
    @JsonProperty("description")
    private final String description;
    @JsonProperty("senderNodeId")
    private final String senderNodeId;
    @JsonProperty("receiverNodeId")
    private final String receiverNodeId;
    @JsonProperty("senderAmount")
    private final BigDecimal senderAmount;
    @JsonProperty("receiverAmount")
    private final BigDecimal receiverAmount;
    @JsonProperty("senderCurrencyId")
    private final String senderCurrencyId;
    @JsonProperty("receiverCurrencyId")
    private final String receiverCurrencyId;
    @JsonProperty("date")
    private final LocalDate date;
    @JsonProperty("cancelled")
    private final boolean isCancelled;
    @JsonProperty("userId")
    private final String userId;

    private TransactionRequestTO(String id, String description, String senderNodeId, String receiverNodeId, BigDecimal senderAmount, BigDecimal receiverAmount, String senderCurrencyId, String receiverCurrencyId, LocalDate date, boolean isCancelled, String userId) {
        this.id = id;
        this.description = description;
        this.senderNodeId = senderNodeId;
        this.receiverNodeId = receiverNodeId;
        this.senderAmount = senderAmount;
        this.receiverAmount = receiverAmount;
        this.senderCurrencyId = senderCurrencyId;
        this.receiverCurrencyId = receiverCurrencyId;
        this.date = date;
        this.isCancelled = isCancelled;
        this.userId = userId;
    }
}
