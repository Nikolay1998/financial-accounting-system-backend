package kraynov.n.financialaccountingsystembackend.service;

import java.math.BigDecimal;
import java.util.Map;

public interface SummaryService {
    public Map<Integer, BigDecimal> getSum();
}
