package kraynov.n.financialaccountingsystembackend.client;

import kraynov.n.financialaccountingsystembackend.to.CurrencyIdPairTo;
import kraynov.n.financialaccountingsystembackend.to.RateTo;

import java.util.Map;
import java.util.Set;

public interface RateClient {
    Map<CurrencyIdPairTo, RateTo> getRates(Set<CurrencyIdPairTo> currencyPairs);
}
