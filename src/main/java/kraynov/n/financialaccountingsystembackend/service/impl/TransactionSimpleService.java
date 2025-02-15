package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.TransactionDAO;
import kraynov.n.financialaccountingsystembackend.dao.TransactionExtendedInfoDAO;
import kraynov.n.financialaccountingsystembackend.exception.AlreadyCanceledException;
import kraynov.n.financialaccountingsystembackend.model.TransactionDto;
import kraynov.n.financialaccountingsystembackend.model.TransactionExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.model.UserDetailsDto;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        return transactionExtendedInfoDAO.getAll(userDTO.getId());
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
    public TransactionDto cancel(String transactionId) throws AlreadyCanceledException {
        LOGGER.debug("Start cancelling transaction {}", transactionId);
        TransactionDto transactionToCancel = transactionDAO.get(transactionId);
        if (transactionToCancel.isCancelled()) {
            throw new AlreadyCanceledException("Transaction already canceled");
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
            throw new IllegalStateException("Can't find transaction for edit with id=" + transaction.getId());
        }

        return transactionExtendedInfoDAO.get(transaction.getId());
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
        return transactionDAO.update(restoredTransaction, userDTO.getId());
    }
}
