package kraynov.n.financialaccountingsystembackend.service;

import java.math.BigDecimal;
import java.util.Map;

public interface SummaryService {
    Map<String, BigDecimal> getSum();
}
