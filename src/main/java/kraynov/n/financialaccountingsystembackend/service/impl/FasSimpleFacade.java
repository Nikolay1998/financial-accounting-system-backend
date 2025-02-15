package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.exception.AlreadyCanceledException;
import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;
import kraynov.n.financialaccountingsystembackend.model.TransactionDto;
import kraynov.n.financialaccountingsystembackend.model.TransactionExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.service.FasFacade;
import kraynov.n.financialaccountingsystembackend.service.NodeService;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;
import org.springframework.transaction.annotation.Transactional;

public class FasSimpleFacade implements FasFacade {
    private final NodeService nodeService;
    private final TransactionService transactionService;

    public FasSimpleFacade(NodeService nodeService, TransactionService transactionService) {
        this.nodeService = nodeService;
        this.transactionService = transactionService;
    }

    @Transactional
    @Override
    public TransactionExtendedInfoDto addTransaction(TransactionDto transaction) throws InsufficientFundsException {
        nodeService.calculateTransactionAffection(transaction);
        return transactionService.add(transaction);
    }

    @Transactional
    @Override
    public TransactionExtendedInfoDto cancelTransaction(String transactionId) throws InsufficientFundsException, AlreadyCanceledException {
        TransactionDto transaction = transactionService.cancel(transactionId);
        nodeService.cancelTransactionAffection(transaction);
        return transactionService.getExtendedInfo(transactionId);
    }

    @Transactional
    @Override
    public TransactionExtendedInfoDto editTransaction(TransactionDto newTransaction) throws InsufficientFundsException {
        TransactionDto old = transactionService.get(newTransaction.getId());
        nodeService.cancelTransactionAffection(old);
        nodeService.calculateTransactionAffection(newTransaction);
        return transactionService.edit(newTransaction);
    }

    @Transactional
    @Override
    public TransactionExtendedInfoDto restoreTransaction(String transactionId) throws InsufficientFundsException {
        TransactionDto transaction = transactionService.restore(transactionId);
        nodeService.calculateTransactionAffection(transaction);
        return transactionService.getExtendedInfo(transactionId);
    }
}
