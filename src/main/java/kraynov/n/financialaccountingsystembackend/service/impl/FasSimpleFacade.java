package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dto.TransactionDto;
import kraynov.n.financialaccountingsystembackend.dto.TransactionExtendedInfoDto;
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
    public TransactionExtendedInfoDto addTransaction(TransactionDto transaction) {
        nodeService.calculateTransactionAffection(transaction);
        return transactionService.add(transaction);
    }

    @Transactional
    @Override
    public TransactionExtendedInfoDto cancelTransaction(String transactionId) {
        TransactionDto transaction = transactionService.cancel(transactionId);
        nodeService.cancelTransactionAffection(transaction);
        return transactionService.getExtendedInfo(transactionId);
    }

    @Transactional
    @Override
    public TransactionExtendedInfoDto editTransaction(TransactionDto newTransaction) {
        TransactionDto old = transactionService.get(newTransaction.getId());
        nodeService.cancelTransactionAffection(old);
        nodeService.calculateTransactionAffection(newTransaction);
        return transactionService.edit(newTransaction);
    }

    @Transactional
    @Override
    public TransactionExtendedInfoDto restoreTransaction(String transactionId) {
        TransactionDto transaction = transactionService.restore(transactionId);
        nodeService.calculateTransactionAffection(transaction);
        return transactionService.getExtendedInfo(transactionId);
    }
}
