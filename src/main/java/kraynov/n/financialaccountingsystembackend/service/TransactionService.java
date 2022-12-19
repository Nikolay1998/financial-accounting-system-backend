package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.model.Transaction;

import java.util.List;

public interface TransactionService {
    Transaction add(Transaction transaction);

    List<Transaction> getAll();

    List<Transaction> getAllBySenderId(int id);

    List<Transaction> getAllByReceiverId(int id);

}
