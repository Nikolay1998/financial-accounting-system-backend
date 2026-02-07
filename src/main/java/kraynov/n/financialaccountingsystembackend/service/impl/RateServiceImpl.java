package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.client.RateClient;
import kraynov.n.financialaccountingsystembackend.service.RateService;
import kraynov.n.financialaccountingsystembackend.to.CurrencyIdPairTo;
import kraynov.n.financialaccountingsystembackend.to.RateTo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RateServiceImpl implements RateService {
    private final RateClient rateServiceClient;

    public RateServiceImpl(RateClient rateClient) {
        this.rateServiceClient = rateClient;
    }

    @Override
    public Map<String, BigDecimal> calculateEquivalents(Map<String, BigDecimal> amountByCurrencies) {
        Map<String, BigDecimal> equivalents = new HashMap<>();
        Set<CurrencyIdPairTo> currencyPairs = generateAllPairs(amountByCurrencies.keySet());
        if (currencyPairs.isEmpty()) {
            return amountByCurrencies;
        }
        Map<CurrencyIdPairTo, RateTo> rates = rateServiceClient.getRates(currencyPairs);
        for (String toCurrency : amountByCurrencies.keySet()) {
            BigDecimal equivalentInCurrentCurrency = amountByCurrencies.get(toCurrency);
            for (String fromCurrency : amountByCurrencies.keySet()) {
                if (fromCurrency.equals(toCurrency)) {
                    continue;
                }
                RateTo rate = rates.get(new CurrencyIdPairTo(fromCurrency, toCurrency));
                equivalentInCurrentCurrency = equivalentInCurrentCurrency.add(
                        amountByCurrencies.get(fromCurrency).multiply(rate.getRate())
                );
            }
            equivalents.put(toCurrency, equivalentInCurrentCurrency);
        }
        return equivalents;
    }

    private Set<CurrencyIdPairTo> generateAllPairs(Set<String> currencyIds) {
        Set<CurrencyIdPairTo> pairs = new HashSet<>();
        for (String from : currencyIds) {
            for (String to : currencyIds) {
                if (!from.equals(to)) {
                    pairs.add(new CurrencyIdPairTo(from, to));
                }
            }
        }
        return pairs;
    }
}
