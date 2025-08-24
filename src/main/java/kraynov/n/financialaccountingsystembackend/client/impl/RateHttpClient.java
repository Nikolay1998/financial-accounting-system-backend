package kraynov.n.financialaccountingsystembackend.client.impl;

import kraynov.n.financialaccountingsystembackend.client.RateClient;
import kraynov.n.financialaccountingsystembackend.to.CurrencyIdPairTo;
import kraynov.n.financialaccountingsystembackend.to.RateTo;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RateHttpClient implements RateClient {
    private final RestTemplate restTemplate;

    public RateHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public Map<CurrencyIdPairTo, RateTo> getRates(Set<CurrencyIdPairTo> currencyPairs) {
        HttpEntity<Set<CurrencyIdPairTo>> request = new HttpEntity<>(currencyPairs);
        ResponseEntity<Set<RateTo>> response = restTemplate.exchange(
                "/rates",
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<>() {
                }
        );

        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException(response.getStatusCode().getReasonPhrase());
        }

        Map<CurrencyIdPairTo, RateTo> map = new HashMap<>();
        for (RateTo rateTo : response.getBody()) {
            map.put(new CurrencyIdPairTo(rateTo.getFromCurrencyId(), rateTo.getToCurrencyId()), rateTo);
        }
        return map;
    }
}
