package kraynov.n.financialaccountingsystembackend.service;


import kraynov.n.financialaccountingsystembackend.to.PeriodStatsTo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public interface SummaryService {
    Map<String, BigDecimal> getSum();

    PeriodStatsTo getBalanceChange(LocalDate from, LocalDate to);

}
