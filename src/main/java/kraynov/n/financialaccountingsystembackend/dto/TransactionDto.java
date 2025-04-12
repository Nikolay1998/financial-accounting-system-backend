package kraynov.n.financialaccountingsystembackend.dto;

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
    private final Integer order;

    private TransactionDto(String id, String description, String senderNodeId, String receiverNodeId, BigDecimal senderAmount, BigDecimal receiverAmount, LocalDate date, boolean isCancelled, String userId, Integer order) {
        this.id = id;
        this.description = description;
        this.senderNodeId = senderNodeId;
        this.receiverNodeId = receiverNodeId;
        this.senderAmount = senderAmount;
        this.receiverAmount = receiverAmount;
        this.date = date;
        this.isCancelled = isCancelled;
        this.userId = userId;
        this.order = order;
    }
}
