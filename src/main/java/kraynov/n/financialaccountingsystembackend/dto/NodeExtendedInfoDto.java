package kraynov.n.financialaccountingsystembackend.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder(toBuilder = true)
public record NodeExtendedInfoDto(
        String id,
        String name,
        String description,
        String currencyId,
        BigDecimal amount,
        String userId,
        boolean isExternal,
        LocalDate lastTransactionDate,
        boolean isOverdraft,
        boolean isArchived
) {
}

