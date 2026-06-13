package kraynov.n.financialaccountingsystembackend.to;

import lombok.Builder;

import java.util.List;

@Builder
public record PeriodStatsTo(
        List<CurrencyBalanceChangeTo> balanceChange,
        List<CurrencyBalanceChangeTo> inAndOut
) {
}
