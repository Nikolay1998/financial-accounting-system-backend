package kraynov.n.financialaccountingsystembackend.to;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record RateTo(
        String fromCurrencyId,
        String toCurrencyId,
        BigDecimal rate
) {
}
