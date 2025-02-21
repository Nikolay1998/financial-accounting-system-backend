package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.CurrencyDAO;
import kraynov.n.financialaccountingsystembackend.dto.CurrencyDto;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;

import java.util.List;

public class CurrencySimpleService implements CurrencyService {

    private final CurrencyDAO currencyDAO;

    public CurrencySimpleService(CurrencyDAO currencyDAO) {
        this.currencyDAO = currencyDAO;
    }

    @Override
    public List<CurrencyDto> getAll() {
        return currencyDAO.getAll();
    }

    @Override
    public CurrencyDto getById(String id) {
        return currencyDAO.getById(id);
    }

}
