package kraynov.n.financialaccountingsystembackend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kraynov.n.financialaccountingsystembackend.model.CurrencyDTO;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;

@RestController
@RequestMapping(path = "/currency")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("getAll")
    public List<CurrencyDTO> getAll() {
        return currencyService.getAll();
    }
}
