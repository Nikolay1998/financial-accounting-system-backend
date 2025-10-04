package kraynov.n.financialaccountingsystembackend.controller;

import kraynov.n.financialaccountingsystembackend.service.RateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/rate")
public class RateController {
    private final RateService rateService;

    public RateController(RateService rateService) {
        this.rateService = rateService;
    }


    @PostMapping(path = "/calculateEquivalents", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, BigDecimal> calculateEquivalents(@RequestBody Map<String, BigDecimal> amountByCurrencies) {
        log.info("calculateEquivalents called");
        return rateService.calculateEquivalents(amountByCurrencies);

    }
}
