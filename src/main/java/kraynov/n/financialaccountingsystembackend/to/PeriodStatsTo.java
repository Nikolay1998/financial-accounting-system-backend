package kraynov.n.financialaccountingsystembackend.to;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PeriodStatsTo {
    private List<CurrencyBalanceChangeTo> balanceChange;
    private List<CurrencyBalanceChangeTo> inAndOut;
}
