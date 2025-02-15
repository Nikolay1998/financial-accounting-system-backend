package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.exception.AlreadyCanceledException;
import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;
import kraynov.n.financialaccountingsystembackend.model.TransactionDto;

public interface FasFacade {
    TransactionDto addTransaction(TransactionDto transaction) throws InsufficientFundsException;

    TransactionDto cancelTransaction(String transactionId) throws InsufficientFundsException, AlreadyCanceledException;

    TransactionDto editTransaction(TransactionDto transaction) throws InsufficientFundsException;

    TransactionDto restoreTransaction(String transactionId) throws InsufficientFundsException;
}
