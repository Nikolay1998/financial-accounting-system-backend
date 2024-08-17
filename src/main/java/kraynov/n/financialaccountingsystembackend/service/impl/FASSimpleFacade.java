package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;
import kraynov.n.financialaccountingsystembackend.model.Transaction;
import kraynov.n.financialaccountingsystembackend.service.FASFacade;
import kraynov.n.financialaccountingsystembackend.service.NodeService;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;
import org.springframework.transaction.annotation.Transactional;

public class FASSimpleFacade implements FASFacade {
    private final NodeService nodeService;
    private final TransactionService transactionService;

    public FASSimpleFacade(NodeService nodeService, TransactionService transactionService) {
        this.nodeService = nodeService;
        this.transactionService = transactionService;
    }

    @Override
    public Transaction addTransaction(Transaction transaction) throws InsufficientFundsException {
        nodeService.calculateTransactionAffection(transaction);
        return transactionService.add(transaction);
    }

    @Override
    public Transaction cancelTransaction(String transactionId) throws InsufficientFundsException {
        Transaction transaction = transactionService.cancel(transactionId);
        nodeService.cancelTransactionAffection(transaction);
        return transaction;
    }

    @Override
    public Transaction editTransaction(Transaction newTransaction) throws InsufficientFundsException {
        Transaction old = transactionService.get(newTransaction.getId());
        nodeService.cancelTransactionAffection(old);
        nodeService.calculateTransactionAffection(newTransaction);
        return transactionService.edit(newTransaction);
    }
}
