package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.TransactionDAO;
import kraynov.n.financialaccountingsystembackend.model.Transaction;
import kraynov.n.financialaccountingsystembackend.model.UserDTO;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleTransactionImpl;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
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
        UserDTO userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        Transaction transactionWithId = SimpleTransactionImpl.builder()
                .from(transaction)
                .setId(UUID.randomUUID().toString())
                .setCancelled(false)
                .setUserId(userDTO.getId())
                .build();
        return transactionDAO.save(transactionWithId);
    }

    @Override
    public Transaction get(String id) {
        return transactionDAO.get(id);
    }

    @Override
    public List<Transaction> getAll() {
        LOGGER.debug("Start loading all transactions");
        //toDo: use userDTO
        contextHolderFacade.getAuthenticatedUser();
        return transactionDAO.getAll();
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
    public Transaction cancel(String transactionId) {
        LOGGER.debug("Start cancelling transaction {}", transactionId);
        transactionDAO.setCancelled(transactionId);
        return transactionDAO.get(transactionId);
    }
}
