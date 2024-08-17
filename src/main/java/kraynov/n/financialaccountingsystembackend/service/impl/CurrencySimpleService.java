package kraynov.n.financialaccountingsystembackend.service.impl;

import java.util.List;

import kraynov.n.financialaccountingsystembackend.dao.CurrencyDAO;
import kraynov.n.financialaccountingsystembackend.model.CurrencyDTO;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;

public class CurrencySimpleService implements CurrencyService {

    private final CurrencyDAO currencyDAO;

    public CurrencySimpleService(CurrencyDAO currencyDAO) {
        this.currencyDAO = currencyDAO;
    }

    @Override
    public List<CurrencyDTO> getAll() {
        return currencyDAO.getAll();
    }

    @Override
    public CurrencyDTO getById(String id) {
        return currencyDAO.getById(id);
    }

}
