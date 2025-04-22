package kraynov.n.financialaccountingsystembackend.service;


import kraynov.n.financialaccountingsystembackend.to.CurrencyBalanceChangeTo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface SummaryService {
    Map<String, BigDecimal> getSum();

    List<CurrencyBalanceChangeTo> getBalanceChange(LocalDate from, LocalDate to);
}
