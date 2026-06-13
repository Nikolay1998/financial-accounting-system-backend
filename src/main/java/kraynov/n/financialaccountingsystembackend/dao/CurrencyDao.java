package kraynov.n.financialaccountingsystembackend.dao;

import kraynov.n.financialaccountingsystembackend.dto.CurrencyDto;

import java.util.List;

public interface CurrencyDao {
    List<CurrencyDto> getAll();

    CurrencyDto getById(String id);
}
