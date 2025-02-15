package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.exception.AlreadyCanceledException;
import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;
import kraynov.n.financialaccountingsystembackend.model.TransactionDto;
import kraynov.n.financialaccountingsystembackend.model.TransactionExtendedInfoDto;

public interface FasFacade {
    TransactionExtendedInfoDto addTransaction(TransactionDto transaction) throws InsufficientFundsException;

    TransactionExtendedInfoDto cancelTransaction(String transactionId) throws InsufficientFundsException, AlreadyCanceledException;

    TransactionExtendedInfoDto editTransaction(TransactionDto transaction) throws InsufficientFundsException;

    TransactionExtendedInfoDto restoreTransaction(String transactionId) throws InsufficientFundsException;
}
