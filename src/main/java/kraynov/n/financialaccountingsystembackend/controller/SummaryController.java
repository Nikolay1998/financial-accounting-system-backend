package kraynov.n.financialaccountingsystembackend.controller;

import kraynov.n.financialaccountingsystembackend.service.SummaryService;
import kraynov.n.financialaccountingsystembackend.to.PeriodStatsTo;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping(path = "/summary")
public class SummaryController {
    private final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @CrossOrigin
    @GetMapping(path = "/sum", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, BigDecimal> getSummary() {
        return summaryService.getSum();
    }

    @CrossOrigin
    @GetMapping(path = "/period-stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public PeriodStatsTo getBalanceChange(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        return summaryService.getBalanceChange(from, to);
    }
}
