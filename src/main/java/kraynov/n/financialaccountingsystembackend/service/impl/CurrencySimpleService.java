package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.CurrencyDao;
import kraynov.n.financialaccountingsystembackend.dto.CurrencyDto;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;

import java.util.List;

public class CurrencySimpleService implements CurrencyService {

    private final CurrencyDao currencyDao;

    public CurrencySimpleService(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    @Override
    public List<CurrencyDto> getAll() {
        return currencyDao.getAll();
    }

    @Override
    public CurrencyDto getById(String id) {
        return currencyDao.getById(id);
    }

}
