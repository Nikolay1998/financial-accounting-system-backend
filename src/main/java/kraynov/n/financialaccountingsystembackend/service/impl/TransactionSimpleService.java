package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.TransactionDAO;
import kraynov.n.financialaccountingsystembackend.exception.AlreadyCanceledException;
import kraynov.n.financialaccountingsystembackend.model.TransactionDto;
import kraynov.n.financialaccountingsystembackend.model.UserDetailsDto;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TransactionSimpleService implements TransactionService {

    private final static Logger LOGGER = LoggerFactory.getLogger(TransactionSimpleService.class);
    private final TransactionDAO transactionDAO;
    private final ContextHolderFacade contextHolderFacade;

    public TransactionSimpleService(TransactionDAO transactionDAO, ContextHolderFacade contextHolderFacade) {
        this.transactionDAO = transactionDAO;
        this.contextHolderFacade = contextHolderFacade;
    }

    @Override
    public TransactionDto add(TransactionDto transaction) {
        LOGGER.debug("Start adding transaction {}", transaction);
        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        TransactionDto transactionWithId = transaction
                .toBuilder()
                .id(UUID.randomUUID().toString())
                .isCancelled(false)
                .userId(userDTO.getId())
                .build();

        //todo: separate pure transaction and transactionWithAdditionalData
        transactionDAO.save(transactionWithId);
        return transactionDAO.get(transactionWithId.getId());
    }

    @Override
    public TransactionDto get(String id) {
        LOGGER.debug("Start loading transaction with id = {}", id);
        return transactionDAO.get(id);
    }

    @Override
    public List<TransactionDto> getAll() {
        LOGGER.debug("Start loading all transactions");

        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        return transactionDAO.getAll(userDTO.getId());
    }

    @Override
    public List<TransactionDto> getAllBySenderId(int id) {
        return transactionDAO.getAllBySenderId(id);
    }

    @Override
    public List<TransactionDto> getAllByReceiverId(int id) {
        return transactionDAO.getAllByReceiverId(id);
    }

    @Override
    public TransactionDto cancel(String transactionId) throws AlreadyCanceledException {
        LOGGER.debug("Start cancelling transaction {}", transactionId);
        TransactionDto transactionToCancel = transactionDAO.get(transactionId);
        if (transactionToCancel.isCancelled()) {
            throw new AlreadyCanceledException("Transaction already canceled");
        }
        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        TransactionDto canceledTransaction = transactionToCancel
                .withCancelled(true);
        transactionDAO.update(canceledTransaction, userDTO.getId());
        return transactionDAO.get(transactionId);
    }

    @Override
    public List<TransactionDto> getAllByNodeId(String id) {
        LOGGER.debug("Start loading all transactions for nodeId = {}", id);
        return transactionDAO.getAllByNodeId(id);
    }

    @Override
    public Optional<TransactionDto> getLastTransactionByNodeId(String id) {
        return getAllByNodeId(id).stream().filter(t -> !t.isCancelled()).findFirst();
    }

    @Override
    public TransactionDto edit(TransactionDto transaction) {
        LOGGER.debug("Start editing transaction {}", transaction);
        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();

        TransactionDto updated = transactionDAO.update(transaction, userDTO.getId());
        if (updated == null) {
            throw new IllegalStateException("Can't find transaction for edit with id=" + transaction.getId());
        }
        return transactionDAO.get(transaction.getId());
    }

    @Override
    public TransactionDto restore(String transactionId) {
        LOGGER.debug("Start restoring transaction {}", transactionId);
        TransactionDto transactionToRestore = transactionDAO.get(transactionId);
        if (!transactionToRestore.isCancelled()) {
            throw new IllegalStateException("Transaction is not canceled");
        }
        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        TransactionDto restoredTransaction = transactionToRestore
                .withCancelled(false);
        transactionDAO.update(restoredTransaction, userDTO.getId());
        return transactionDAO.get(transactionId);
    }
}
