package kraynov.n.financialaccountingsystembackend.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder(toBuilder = true)
public record TransactionExtendedInfoDto(
        String id,
        String description,
        String senderNodeId,
        String receiverNodeId,
        BigDecimal senderAmount,
        BigDecimal receiverAmount,
        LocalDate date,
        boolean isCancelled,
        String userId,
        boolean isFromExternal,
        boolean isToExternal,
        String senderCurrencyId,
        String receiverCurrencyId,
        String senderName,
        String receiverName,
        Integer order
) {
}