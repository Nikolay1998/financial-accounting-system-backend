package kraynov.n.financialaccountingsystembackend.dao.impl;

import kraynov.n.financialaccountingsystembackend.dao.CurrencyDAO;
import kraynov.n.financialaccountingsystembackend.dto.CurrencyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

public class CurrencyCachedDAO implements CurrencyDAO {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CurrencyDAO currencyDAO;
    private final Map<String, CurrencyDto> currencies = new HashMap<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public CurrencyCachedDAO(CurrencyDAO currencyDAO) {
        this.currencyDAO = currencyDAO;
    }

    @Override
    public List<CurrencyDto> getAll() {
        lock.readLock().lock();
        if (currencies.isEmpty()) {
            lock.readLock().unlock();
            lock.writeLock().lock();
            if (currencies.isEmpty()) {
                currencies.putAll(currencyDAO.getAll().stream().collect(Collectors.toMap(CurrencyDto::getId, c -> c)));
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
        CurrencyDto currencyDTO = currencies.get(id);
        if (currencyDTO == null) {
            logger.warn("getById : {}, currencies : {}", id, currencies);
        }
        return currencyDTO;
    }
}
