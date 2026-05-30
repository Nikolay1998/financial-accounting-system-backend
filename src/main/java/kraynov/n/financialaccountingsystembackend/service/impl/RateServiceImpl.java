package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.client.RateClient;
import kraynov.n.financialaccountingsystembackend.dto.NodeExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.NodeService;
import kraynov.n.financialaccountingsystembackend.service.RateService;
import kraynov.n.financialaccountingsystembackend.to.CurrencyIdPairTo;
import kraynov.n.financialaccountingsystembackend.to.RateTo;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class RateServiceImpl implements RateService {
    private final RateClient rateServiceClient;
    private final NodeService nodeService;
    private final ContextHolderFacade contextHolderFacade; //todo: move all work with it to controller and propagate userId

    public RateServiceImpl(
            RateClient rateClient,
            NodeService nodeService,
            ContextHolderFacade contextHolderFacade
    ) {
        this.rateServiceClient = rateClient;
        this.nodeService = nodeService;
        this.contextHolderFacade = contextHolderFacade;
    }

    @Override
    public Map<String, BigDecimal> calculateEquivalents(Map<String, BigDecimal> amountByCurrencies) {
        Set<String> userCurrencies = getUserCurrencies();
        if (userCurrencies.isEmpty() || userCurrencies.size() == 1) {
            return amountByCurrencies;
        }
        Set<String> incomingCurrencies = amountByCurrencies.keySet();

        Set<CurrencyIdPairTo> pairs = generateAllPairs(incomingCurrencies, userCurrencies);
        Map<CurrencyIdPairTo, RateTo> rates = rateServiceClient.getRates(pairs);

        Map<String, BigDecimal> equivalents = new HashMap<>();
        for (String toCurrency : userCurrencies) {
            BigDecimal equivalentInCurrentCurrency = BigDecimal.ZERO;
            for (String fromCurrency : incomingCurrencies) {
                if (fromCurrency.equals(toCurrency)) {
                    continue;
                }
                RateTo rate = rates.get(new CurrencyIdPairTo(fromCurrency, toCurrency));
                if (rate == null) {
                    throw new RuntimeException("Rate for currency pair: from " + fromCurrency + " to " + toCurrency + " not found");
                }
                equivalentInCurrentCurrency = equivalentInCurrentCurrency.add(
                        amountByCurrencies.getOrDefault(fromCurrency, BigDecimal.ZERO).multiply(rate.rate()));
            }
            equivalentInCurrentCurrency = equivalentInCurrentCurrency
                    .add(amountByCurrencies.getOrDefault(toCurrency, BigDecimal.ZERO));
            equivalents.put(toCurrency, equivalentInCurrentCurrency);
        }
        return equivalents;
    }

    private Set<String> getUserCurrencies() {
        String userId = contextHolderFacade.getAuthenticatedUser().getId();
        return nodeService.getAllByUser(userId)
                .stream()
                .map(NodeExtendedInfoDto::currencyId)
                .collect(Collectors.toSet());
    }

    private Set<CurrencyIdPairTo> generateAllPairs(
            Set<String> incomingCurrencies,
            Set<String> userCurrencies
    ) {
        Set<CurrencyIdPairTo> pairs = new HashSet<>();
        for (String from : incomingCurrencies) {
            for (String to : userCurrencies) {
                if (!from.equals(to)) {
                    pairs.add(new CurrencyIdPairTo(from, to));
                }
            }
        }
        return pairs;
    }
}
