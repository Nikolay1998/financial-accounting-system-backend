package kraynov.n.financialaccountingsystembackend.dao;

import java.util.List;

import kraynov.n.financialaccountingsystembackend.model.Transaction;

public interface TransactionDAO {
    Transaction save(Transaction transaction);

    Transaction get(String transactionId);

    List<Transaction> getAll(String usedId);

    List<Transaction> getAllBySenderId(int id);

    List<Transaction> getAllByReceiverId(int id);

    List<Transaction> getAllByNodeId(String id);

    int setCancelled(String id);
}
