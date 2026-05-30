package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.TransactionDao;
import kraynov.n.financialaccountingsystembackend.dao.TransactionExtendedInfoDao;
import kraynov.n.financialaccountingsystembackend.dto.TransactionDto;
import kraynov.n.financialaccountingsystembackend.dto.TransactionExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.dto.TransactionFilterDto;
import kraynov.n.financialaccountingsystembackend.dto.UserDetailsDto;
import kraynov.n.financialaccountingsystembackend.exception.InvalidOperationException;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.sql.BatchUpdateException;
import java.util.List;
import java.util.UUID;

public class TransactionSimpleService implements TransactionService {

    private final static Logger LOGGER = LoggerFactory.getLogger(TransactionSimpleService.class);
    private final TransactionExtendedInfoDao transactionExtendedInfoDao;
    private final TransactionDao transactionDao;
    private final ContextHolderFacade contextHolderFacade;

    public TransactionSimpleService(
            TransactionExtendedInfoDao transactionExtendedInfoDao,
            TransactionDao transactionDao,
            ContextHolderFacade contextHolderFacade
    ) {
        this.transactionExtendedInfoDao = transactionExtendedInfoDao;
        this.transactionDao = transactionDao;
        this.contextHolderFacade = contextHolderFacade;
    }

    @Override
    public TransactionExtendedInfoDto add(TransactionDto transaction) {
        LOGGER.debug("Start adding transaction {}", transaction);
        UserDetailsDto userDto = contextHolderFacade.getAuthenticatedUserOrThrowException();
        TransactionDto transactionWithId = transaction
                .toBuilder()
                .id(UUID.randomUUID().toString())
                .isCancelled(false)
                .userId(userDto.getId())
                .build();

        transactionDao.save(transactionWithId);
        return transactionExtendedInfoDao.get(transactionWithId.id());
    }

    @Override
    public TransactionDto get(String id) {
        LOGGER.debug("Start loading transaction with id = {}", id);
        return transactionDao.get(id);
    }

    @Override
    public TransactionExtendedInfoDto getExtendedInfo(String id) {
        return transactionExtendedInfoDao.get(id);
    }

    @Override
    public List<TransactionExtendedInfoDto> getAll() {
        LOGGER.debug("Start loading all transactions");

        UserDetailsDto userDto = contextHolderFacade.getAuthenticatedUserOrThrowException();
        return transactionExtendedInfoDao.getAllByUserId(userDto.getId());
    }

    @Override
    public List<TransactionExtendedInfoDto> getAllBySenderId(int id) {
        return transactionExtendedInfoDao.getAllBySenderId(id);
    }

    @Override
    public List<TransactionExtendedInfoDto> getAllByReceiverId(int id) {
        return transactionExtendedInfoDao.getAllByReceiverId(id);
    }

    @Override
    public TransactionDto cancel(String transactionId) {
        LOGGER.debug("Start cancelling transaction {}", transactionId);
        TransactionDto transactionToCancel = transactionDao.get(transactionId);
        if (transactionToCancel.isCancelled()) {
            throw new InvalidOperationException(
                    String.format("Transaction %s has been cancelled", transactionId),
                    "transaction already canceled");
        }
        UserDetailsDto userDto = contextHolderFacade.getAuthenticatedUserOrThrowException();
        TransactionDto canceledTransaction = transactionToCancel
                .withCancelled(true);

        return transactionDao.update(canceledTransaction, userDto.getId());
    }

    @Override
    public List<TransactionExtendedInfoDto> getAllByNodeId(String id) {
        LOGGER.debug("Start loading all transactions for nodeId = {}", id);
        return transactionExtendedInfoDao.getAllByNodeId(id);
    }

    @Override
    public TransactionExtendedInfoDto edit(TransactionDto transaction) {
        LOGGER.debug("Start editing transaction {}", transaction);
        UserDetailsDto userDto = contextHolderFacade.getAuthenticatedUserOrThrowException();

        TransactionDto updated = transactionDao.update(transaction, userDto.getId());
        if (updated == null) {
            throw new InvalidOperationException(
                    String.format("Can't find transaction for edit with id = %s", transaction.id()),
                    "transaction " + transaction.description() + " not found");
        }

        return transactionExtendedInfoDao.get(transaction.id());
    }

    @Override
    public TransactionDto restore(String transactionId) {
        LOGGER.debug("Start restoring transaction {}", transactionId);
        TransactionDto transactionToRestore = transactionDao.get(transactionId);
        if (!transactionToRestore.isCancelled()) {
            throw new InvalidOperationException(
                    String.format("transaction %s is not canceled", transactionToRestore.id()),
                    String.format("transaction %s is not canceled", transactionToRestore.description()));
        }
        UserDetailsDto userDto = contextHolderFacade.getAuthenticatedUserOrThrowException();
        TransactionDto restoredTransaction = transactionToRestore
                .withCancelled(false);
        return transactionDao.update(restoredTransaction, userDto.getId());
    }

    @Transactional
    @Override
    public List<TransactionExtendedInfoDto> swapOrder(
            String firstTransactionId,
            String secondTransactionId
    ) {
        LOGGER.debug("Start swapping transactions {} and {}", firstTransactionId, secondTransactionId);
        List<TransactionDto> pairToSwap = transactionDao.getAllByIds(List.of(firstTransactionId, secondTransactionId));
        if (!pairToSwap.get(0).date().isEqual(pairToSwap.get(1).date())) {
            throw new InvalidOperationException(String.format("Transactions have different date: %s and %s",
                                                              pairToSwap.get(0).date(), pairToSwap.get(1).date()),
                                                "Only transactions with same date are available to move");
        }
        TransactionDto firstUpdatedTransaction = pairToSwap.get(0).toBuilder().order(pairToSwap.get(1).order()).build();
        TransactionDto secondUpdatedTransaction = pairToSwap.get(1).toBuilder().order(pairToSwap.get(0).order()).build();
        try {
            transactionDao.batchUpdate(List.of(firstUpdatedTransaction, secondUpdatedTransaction));
        } catch (BatchUpdateException e) {
            throw new RuntimeException(e);
        }

        return transactionExtendedInfoDao.getAllByIds(List.of(firstTransactionId, secondTransactionId));
    }

    @Override
    public List<TransactionExtendedInfoDto> getAllByFilter(TransactionFilterDto filter) {
        UserDetailsDto userDto = contextHolderFacade.getAuthenticatedUserOrThrowException();
        if (filter.from().isAfter(filter.to())) {
            throw new InvalidOperationException("From date should be before to", "From date should be before to");
        }
        return transactionExtendedInfoDao.getAllByFilter(filter, userDto.getId());
    }
}
