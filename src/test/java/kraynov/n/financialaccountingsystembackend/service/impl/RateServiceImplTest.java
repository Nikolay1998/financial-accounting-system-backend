package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.client.RateClient;
import kraynov.n.financialaccountingsystembackend.dto.NodeExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.NodeService;
import kraynov.n.financialaccountingsystembackend.to.CurrencyIdPairTo;
import kraynov.n.financialaccountingsystembackend.to.RateTo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RateServiceImplTest {

    private final String userId = "user-123";
    @Mock
    private RateClient rateClient;
    @Mock
    private NodeService nodeService;
    @Mock
    private ContextHolderFacade contextHolderFacade;
    @InjectMocks
    private RateServiceImpl rateService;

    @BeforeEach
    void setUp() {
        // Default behavior for authenticated user
        when(contextHolderFacade.getAuthenticatedUser()).thenReturn(
                new kraynov.n.financialaccountingsystembackend.dto.UserDetailsDto(userId, "user", "pass")
        );
    }

    @Test
    void calculateEquivalents_returnsEquivalentsOnlyForUserCurrencies() {
        // User owns nodes in RUB, USD and EUR
        List<NodeExtendedInfoDto> nodes = Arrays.asList(
                NodeExtendedInfoDto.builder().id("n1").currencyId("RUB").build(),
                NodeExtendedInfoDto.builder().id("n2").currencyId("USD").build(),
                NodeExtendedInfoDto.builder().id("n3").currencyId("EUR").build()
        );
        when(nodeService.getAllByUser(userId)).thenReturn(nodes);

        // Amounts for RUB and USD
        Map<String, BigDecimal> amounts = new HashMap<>();
        amounts.put("RUB", new BigDecimal("100"));
        amounts.put("USD", new BigDecimal("200"));

        // Prepare rates for all pairs among RUB, USD, EUR
        Map<CurrencyIdPairTo, RateTo> rates = new HashMap<>();
        // USD -> RUB
        rates.put(new CurrencyIdPairTo("USD", "RUB"), RateTo.builder().rate(new BigDecimal("75")).build());
        // RUB -> USD
        rates.put(new CurrencyIdPairTo("RUB", "USD"), RateTo.builder().rate(new BigDecimal("0.013333333")).build());
        // EUR -> RUB
        rates.put(new CurrencyIdPairTo("EUR", "RUB"), RateTo.builder().rate(new BigDecimal("90")).build());
        // RUB -> EUR
        rates.put(new CurrencyIdPairTo("RUB", "EUR"), RateTo.builder().rate(new BigDecimal("0.011111111")).build());
        // USD -> EUR
        rates.put(new CurrencyIdPairTo("USD", "EUR"), RateTo.builder().rate(new BigDecimal("0.9")).build());
        // EUR -> USD
        rates.put(new CurrencyIdPairTo("EUR", "USD"), RateTo.builder().rate(new BigDecimal("1.1")).build());

        when(rateClient.getRates(anySet())).thenReturn(rates);

        // Execute
        Map<String, BigDecimal> result = rateService.calculateEquivalents(amounts);

        // Verify that the result contains all user currencies - RUB, USD and EUR
        assertEquals(3, result.size());
        assertTrue(result.containsKey("RUB"));
        assertTrue(result.containsKey("USD"));
        assertTrue(result.containsKey("EUR"));

        // Expected calculations
        // RUB: 100 + 200*75 = 100 + 15000 = 15100
        BigDecimal expectedRUB = new BigDecimal("15100");
        // USD: 200 + 100*0.013333333 = 200 + 1.3333333 = 201.3333333
        BigDecimal expectedUSD = new BigDecimal("201.3333333");
        // EUR: 100*0.011111111 + 200*0.9 = 1.1111111 + 180 = 181.1111111
        BigDecimal expectedEUR = new BigDecimal("181.1111111");

        assertEquals(0, expectedRUB.compareTo(result.get("RUB")));
        assertEquals(0, expectedUSD.compareTo(result.get("USD")));
        assertEquals(0, expectedEUR.compareTo(result.get("EUR")));
    }

    @Test
    void calculateEquivalents_returnsIncomingMapWhenUserHasNoNodes() {
        when(nodeService.getAllByUser(userId)).thenReturn(Collections.emptyList());

        Map<String, BigDecimal> amounts = new HashMap<>();
        amounts.put("RUB", new BigDecimal("100"));

        Map<String, BigDecimal> result = rateService.calculateEquivalents(amounts);

        assertEquals(1, result.size());
        assertEquals(new BigDecimal(100), result.get("RUB"));
        verify(rateClient, never()).getRates(anySet());
    }

    @Test
    void calculateEquivalents_handlesMissingRatesGracefully() {
        // User owns nodes in USD and EUR
        List<NodeExtendedInfoDto> nodes = Arrays.asList(
                NodeExtendedInfoDto.builder().id("n1").currencyId("USD").build(),
                NodeExtendedInfoDto.builder().id("n2").currencyId("EUR").build()
        );
        when(nodeService.getAllByUser(userId)).thenReturn(nodes);

        Map<String, BigDecimal> amounts = new HashMap<>();
        amounts.put("USD", new BigDecimal("100"));
        amounts.put("EUR", new BigDecimal("200"));

        // Provide only USD->EUR rate; EUR->USD rate is missing
        Map<CurrencyIdPairTo, RateTo> rates = new HashMap<>();
        rates.put(new CurrencyIdPairTo("USD", "EUR"), RateTo.builder().rate(new BigDecimal("0.8")).build());

        when(rateClient.getRates(anySet())).thenReturn(rates);
        Assertions.assertThrows(RuntimeException.class, () -> rateService.calculateEquivalents(amounts));
    }
}
