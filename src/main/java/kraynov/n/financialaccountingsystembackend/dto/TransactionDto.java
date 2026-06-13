package kraynov.n.financialaccountingsystembackend.dto;

import lombok.Builder;
import lombok.With;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder(toBuilder = true)
@With
public record TransactionDto(
        String id,
        String description,
        String senderNodeId,
        String receiverNodeId,
        BigDecimal senderAmount,
        BigDecimal receiverAmount,
        LocalDate date,
        boolean isCancelled,
        String userId,
        Integer order
) {
}
