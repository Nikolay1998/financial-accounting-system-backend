package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.dto.CurrencyDto;

import java.util.List;

public interface CurrencyService {
    List<CurrencyDto> getAll();

    CurrencyDto getById(String id);
}
