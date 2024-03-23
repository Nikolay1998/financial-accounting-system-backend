package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;
import kraynov.n.financialaccountingsystembackend.model.Transaction;

public interface FASFacade {
    Transaction addTransaction(Transaction transaction) throws InsufficientFundsException;

    Transaction cancelTransaction(String transactionId) throws InsufficientFundsException;

}
