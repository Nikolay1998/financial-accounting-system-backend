package kraynov.n.financialaccountingsystembackend.to;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record CurrencyBalanceChangeTo(
        String currencyId,
        BigDecimal totalChange,
        BigDecimal income,
        BigDecimal outgo
) {
}
