package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.TransactionDAO;
import kraynov.n.financialaccountingsystembackend.dao.TransactionExtendedInfoDAO;
import kraynov.n.financialaccountingsystembackend.dto.TransactionDto;
import kraynov.n.financialaccountingsystembackend.dto.TransactionExtendedInfoDto;
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
    private final TransactionExtendedInfoDAO transactionExtendedInfoDAO;
    private final TransactionDAO transactionDAO;
    private final ContextHolderFacade contextHolderFacade;

    public TransactionSimpleService(TransactionExtendedInfoDAO transactionExtendedInfoDAO, TransactionDAO transactionDAO, ContextHolderFacade contextHolderFacade) {
        this.transactionExtendedInfoDAO = transactionExtendedInfoDAO;
        this.transactionDAO = transactionDAO;
        this.contextHolderFacade = contextHolderFacade;
    }

    @Override
    public TransactionExtendedInfoDto add(TransactionDto transaction) {
        LOGGER.debug("Start adding transaction {}", transaction);
        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        TransactionDto transactionWithId = transaction
                .toBuilder()
                .id(UUID.randomUUID().toString())
                .isCancelled(false)
                .userId(userDTO.getId())
                .build();

        transactionDAO.save(transactionWithId);
        return transactionExtendedInfoDAO.get(transactionWithId.getId());
    }

    @Override
    public TransactionDto get(String id) {
        LOGGER.debug("Start loading transaction with id = {}", id);
        return transactionDAO.get(id);
    }

    @Override
    public TransactionExtendedInfoDto getExtendedInfo(String id) {
        return transactionExtendedInfoDAO.get(id);
    }

    @Override
    public List<TransactionExtendedInfoDto> getAll() {
        LOGGER.debug("Start loading all transactions");

        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        return transactionExtendedInfoDAO.getAllByUserId(userDTO.getId());
    }

    @Override
    public List<TransactionExtendedInfoDto> getAllBySenderId(int id) {
        return transactionExtendedInfoDAO.getAllBySenderId(id);
    }

    @Override
    public List<TransactionExtendedInfoDto> getAllByReceiverId(int id) {
        return transactionExtendedInfoDAO.getAllByReceiverId(id);
    }

    @Override
    public TransactionDto cancel(String transactionId) {
        LOGGER.debug("Start cancelling transaction {}", transactionId);
        TransactionDto transactionToCancel = transactionDAO.get(transactionId);
        if (transactionToCancel.isCancelled()) {
            throw new InvalidOperationException(
                    String.format("Transaction %s has been cancelled", transactionId),
                    "transaction already canceled");
        }
        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        TransactionDto canceledTransaction = transactionToCancel
                .withCancelled(true);

        return transactionDAO.update(canceledTransaction, userDTO.getId());
    }

    @Override
    public List<TransactionExtendedInfoDto> getAllByNodeId(String id) {
        LOGGER.debug("Start loading all transactions for nodeId = {}", id);
        return transactionExtendedInfoDAO.getAllByNodeId(id);
    }

    @Override
    public TransactionExtendedInfoDto edit(TransactionDto transaction) {
        LOGGER.debug("Start editing transaction {}", transaction);
        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();

        TransactionDto updated = transactionDAO.update(transaction, userDTO.getId());
        if (updated == null) {
            throw new InvalidOperationException(
                    String.format("Can't find transaction for edit with id = %s", transaction.getId()),
                    "transaction " + transaction.getDescription() + " not found");
        }

        return transactionExtendedInfoDAO.get(transaction.getId());
    }

    @Override
    public TransactionDto restore(String transactionId) {
        LOGGER.debug("Start restoring transaction {}", transactionId);
        TransactionDto transactionToRestore = transactionDAO.get(transactionId);
        if (!transactionToRestore.isCancelled()) {
            throw new InvalidOperationException(
                    String.format("transaction %s is not canceled", transactionToRestore.getId()),
                    String.format("transaction %s is not canceled", transactionToRestore.getDescription()));
        }
        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        TransactionDto restoredTransaction = transactionToRestore
                .withCancelled(false);
        return transactionDAO.update(restoredTransaction, userDTO.getId());
    }

    @Transactional
    @Override
    public List<TransactionExtendedInfoDto> swapOrder(String firstTransactionId, String secondTransactionId) {
        LOGGER.debug("Start swapping transactions {} and {}", firstTransactionId, secondTransactionId);
        List<TransactionDto> pairToSwap = transactionDAO.getAllByIds(List.of(firstTransactionId, secondTransactionId));
        if (!pairToSwap.get(0).getDate().isEqual(pairToSwap.get(1).getDate())) {
            throw new InvalidOperationException(String.format("Transactions have different date: %s and %s",
                    pairToSwap.get(0).getDate(), pairToSwap.get(1).getDate()),
                    "Only transactions with same date are available to move");
        }
        TransactionDto firstUpdatedTransaction = pairToSwap.get(0).toBuilder().order(pairToSwap.get(1).getOrder()).build();
        TransactionDto secondUpdatedTransaction = pairToSwap.get(1).toBuilder().order(pairToSwap.get(0).getOrder()).build();
        try {
            transactionDAO.batchUpdate(List.of(firstUpdatedTransaction, secondUpdatedTransaction));
        } catch (BatchUpdateException e) {
            throw new RuntimeException(e);
        }

        return transactionExtendedInfoDAO.getAllByIds(List.of(firstTransactionId, secondTransactionId));
    }
}
