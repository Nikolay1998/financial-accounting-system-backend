package kraynov.n.financialaccountingsystembackend.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class TransactionResponseTO {
    @JsonProperty("id")
    private final String id;
    @JsonProperty("description")
    private final String description;
    @JsonProperty("senderNodeId")
    private final String senderNodeId;
    @JsonProperty("receiverNodeId")
    private final String receiverNodeId;
    @JsonProperty("senderNodeName")
    private final String senderNodeName;
    @JsonProperty("receiverNodeName")
    private final String receiverNodeName;
    @JsonProperty("senderAmount")
    private final BigDecimal senderAmount;
    @JsonProperty("receiverAmount")
    private final BigDecimal receiverAmount;
    @JsonProperty("senderCurrencyId")
    private final String senderCurrencyId;
    @JsonProperty("senderCurrencySymbol")
    private final String senderCurrencySymbol;
    @JsonProperty("receiverCurrencyId")
    private final String receiverCurrencyId;
    @JsonProperty("receiverCurrencySymbol")
    private final String receiverCurrencySymbol;
    @JsonProperty("date")
    private final LocalDate date;
    @JsonProperty("cancelled")
    private final boolean isCancelled;
    @JsonProperty("userId")
    private final String userId;
    @JsonProperty("fromExternal")
    private final boolean isFromExternal;
    @JsonProperty("toExternal")
    private final boolean isToExternal;
    @JsonProperty("order")
    private final int order;

    private TransactionResponseTO(String id, String description, String senderNodeId, String receiverNodeId, String senderNodeName, String receiverNodeName, BigDecimal senderAmount, BigDecimal receiverAmount, String senderCurrencyId, String senderCurrencySymbol, String receiverCurrencyId, String receiverCurrencySymbol, LocalDate date, boolean isCancelled, String userId, boolean isFromExternal, boolean isToExternal, int order) {
        this.id = id;
        this.description = description;
        this.senderNodeId = senderNodeId;
        this.receiverNodeId = receiverNodeId;
        this.senderNodeName = senderNodeName;
        this.receiverNodeName = receiverNodeName;
        this.senderAmount = senderAmount;
        this.receiverAmount = receiverAmount;
        this.senderCurrencyId = senderCurrencyId;
        this.senderCurrencySymbol = senderCurrencySymbol;
        this.receiverCurrencyId = receiverCurrencyId;
        this.receiverCurrencySymbol = receiverCurrencySymbol;
        this.date = date;
        this.isCancelled = isCancelled;
        this.userId = userId;
        this.isFromExternal = isFromExternal;
        this.isToExternal = isToExternal;
        this.order = order;
    }
}
