package kraynov.n.financialaccountingsystembackend.dao.impl;

import kraynov.n.financialaccountingsystembackend.dao.CurrencyDao;
import kraynov.n.financialaccountingsystembackend.dto.CurrencyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class CurrencyCachedDao implements CurrencyDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CurrencyDao currencyDao;
    private final Map<String, CurrencyDto> currencies = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public CurrencyCachedDao(CurrencyDao currencyDao) {
        this.currencyDao = currencyDao;
    }

    @Override
    public List<CurrencyDto> getAll() {
        lock.readLock().lock();
        if (currencies.isEmpty()) {
            lock.readLock().unlock();
            lock.writeLock().lock();
            if (currencies.isEmpty()) {
                currencies.putAll(currencyDao.getAll().stream().collect(Collectors.toMap(CurrencyDto::getId, c -> c)));
            }
            lock.writeLock().unlock();
            return List.copyOf(currencies.values());
        }
        lock.readLock().unlock();
        return List.copyOf(currencies.values());
    }

    @Override
    public CurrencyDto getById(String id) {
        getAll();
        CurrencyDto currencyDto = currencies.get(id);
        if (currencyDto == null) {
            logger.warn("getById : {}, currencies : {}", id, currencies);
        }
        return currencyDto;
    }
}
