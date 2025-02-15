package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.TransactionDAO;
import kraynov.n.financialaccountingsystembackend.exception.AlreadyCanceledException;
import kraynov.n.financialaccountingsystembackend.model.Transaction;
import kraynov.n.financialaccountingsystembackend.model.UserDTO;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleTransactionImpl;
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
    public Transaction add(Transaction transaction) {
        LOGGER.debug("Start adding transaction {}", transaction);
        UserDTO userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        Transaction transactionWithId = SimpleTransactionImpl.builder()
                .from(transaction)
                .setId(UUID.randomUUID().toString())
                .setCancelled(false)
                .setUserId(userDTO.getId())
                .build();

        //todo: separate pure transaction and transactionWithAdditionalData
        transactionDAO.save(transactionWithId);
        return transactionDAO.get(transactionWithId.getId());
    }

    @Override
    public Transaction get(String id) {
        LOGGER.debug("Start loading transaction with id = {}", id);
        return transactionDAO.get(id);
    }

    @Override
    public List<Transaction> getAll() {
        LOGGER.debug("Start loading all transactions");

        UserDTO userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        return transactionDAO.getAll(userDTO.getId());

    }

    @Override
    public List<Transaction> getAllBySenderId(int id) {
        return transactionDAO.getAllBySenderId(id);
    }

    @Override
    public List<Transaction> getAllByReceiverId(int id) {
        return transactionDAO.getAllByReceiverId(id);
    }

    @Override
    public Transaction cancel(String transactionId) throws AlreadyCanceledException {
        LOGGER.debug("Start cancelling transaction {}", transactionId);
        Transaction transactionToCancel = transactionDAO.get(transactionId);
        if (transactionToCancel.isCancelled()) {
            throw new AlreadyCanceledException("Transaction already canceled");
        }
        UserDTO userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        Transaction canceledTransaction = SimpleTransactionImpl.builder().from(transactionToCancel)
                .setCancelled(true)
                .build();
        transactionDAO.update(canceledTransaction, userDTO.getId());
        return transactionDAO.get(transactionId);
    }

    @Override
    public List<Transaction> getAllByNodeId(String id) {
        LOGGER.debug("Start loading all transactions for nodeId = {}", id);
        return transactionDAO.getAllByNodeId(id);

    }

    @Override
    public Optional<Transaction> getLastTransactionByNodeId(String id) {
        return getAllByNodeId(id).stream().filter(t -> !t.isCancelled()).findFirst();
    }

    @Override
    public Transaction edit(Transaction transaction) {
        LOGGER.debug("Start editing transaction {}", transaction);
        UserDTO userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();

        Transaction updated = transactionDAO.update(transaction, userDTO.getId());
        if (updated == null) {
            throw new IllegalStateException("Can't find transaction for edit with id=" + transaction.getId());
        }
        return transactionDAO.get(transaction.getId());
    }

    @Override
    public Transaction restore(String transactionId) {
        LOGGER.debug("Start restoring transaction {}", transactionId);
        Transaction transactionToRestore = transactionDAO.get(transactionId);
        if (!transactionToRestore.isCancelled()) {
            throw new IllegalStateException("Transaction is not canceled");
        }
        UserDTO userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        Transaction restoredTransaction = SimpleTransactionImpl.builder().from(transactionToRestore)
                .setCancelled(false)
                .build();
        transactionDAO.update(restoredTransaction, userDTO.getId());
        return transactionDAO.get(transactionId);
    }
}
