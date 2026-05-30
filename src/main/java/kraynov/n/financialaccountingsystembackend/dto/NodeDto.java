package kraynov.n.financialaccountingsystembackend.dto;

import lombok.Builder;
import lombok.With;

import java.math.BigDecimal;

@Builder(toBuilder = true)
@With
public record NodeDto(
        String id,
        String name,
        String description,
        String currencyId,
        BigDecimal amount,
        String userId,
        boolean isExternal,
        boolean isOverdraft,
        boolean isArchived
) {
}
