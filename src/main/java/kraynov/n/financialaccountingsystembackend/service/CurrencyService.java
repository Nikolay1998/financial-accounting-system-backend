package kraynov.n.financialaccountingsystembackend.service;

import java.util.List;

import kraynov.n.financialaccountingsystembackend.model.CurrencyDTO;

public interface CurrencyService {
    List<CurrencyDTO> getAll();

    CurrencyDTO getById(String id);
}
