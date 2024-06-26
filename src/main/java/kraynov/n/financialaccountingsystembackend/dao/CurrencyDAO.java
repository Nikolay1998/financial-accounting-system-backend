package kraynov.n.financialaccountingsystembackend.dao;

import java.util.List;

import kraynov.n.financialaccountingsystembackend.model.CurrencyDTO;

public interface CurrencyDAO {
    List<CurrencyDTO> getAll();

    CurrencyDTO getById(String id);
}
