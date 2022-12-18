package kraynov.n.financialaccountingsystembackend.dao;

import kraynov.n.financialaccountingsystembackend.model.Transaction;

import java.util.List;

public interface TransactionDAO {
    Transaction save(Transaction transaction);

    List<Transaction> getAll();

    List<Transaction> getAllBySenderId(int id);

    List<Transaction> getAllByReceiverId(int id);
}
