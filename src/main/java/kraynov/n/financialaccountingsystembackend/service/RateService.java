package kraynov.n.financialaccountingsystembackend.service;


import java.math.BigDecimal;
import java.util.Map;

public interface RateService {
    Map<String, BigDecimal> calculateEquivalents(Map<String, BigDecimal> amountByCurrencies);
}
