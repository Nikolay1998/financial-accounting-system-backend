package kraynov.n.financialaccountingsystembackend.to;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CurrencyBalanceChangeTo {
    private final String currencyId;
    private final BigDecimal totalChange;
    private final BigDecimal income;
    private final BigDecimal outgo;
}
