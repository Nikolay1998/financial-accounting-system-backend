package kraynov.n.financialaccountingsystembackend.model;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
@With
public class TransactionDto {
    private final String id;
    private final String description;
    private final String senderNodeId;
    private final String receiverNodeId;
    private final BigDecimal senderAmount;
    private final BigDecimal receiverAmount;
    private final LocalDate date;
    private final boolean isCancelled;
    private final String userId;
    private final boolean isFromExternal;
    private final boolean isToExternal;
    private final String senderCurrencyId;
    private final String receiverCurrencyId;
    private final String senderName;
    private final String receiverName;

    private TransactionDto(String id, String description, String senderNodeId, String receiverNodeId, BigDecimal senderAmount, BigDecimal receiverAmount, LocalDate date, boolean isCancelled, String userId, boolean isFromExternal, boolean isToExternal, String senderCurrencyId, String receiverCurrencyId, String senderName, String receiverName) {
        this.id = id;
        this.description = description;
        this.senderNodeId = senderNodeId;
        this.receiverNodeId = receiverNodeId;
        this.senderAmount = senderAmount;
        this.receiverAmount = receiverAmount;
        this.date = date;
        this.isCancelled = isCancelled;
        this.userId = userId;
        this.isFromExternal = isFromExternal;
        this.isToExternal = isToExternal;
        this.senderCurrencyId = senderCurrencyId;
        this.receiverCurrencyId = receiverCurrencyId;
        this.senderName = senderName;
        this.receiverName = receiverName;
    }
}