package kraynov.n.financialaccountingsystembackend.dao;

import kraynov.n.financialaccountingsystembackend.model.Transaction;

import java.util.List;

public interface TransactionDAO {
    Transaction save(Transaction transaction);

    Transaction get(String transactionId);

    List<Transaction> getAll(String usedId);

    List<Transaction> getAllBySenderId(int id);

    List<Transaction> getAllByReceiverId(int id);

    List<Transaction> getAllByNodeId(String id);

    Transaction update(Transaction transaction, String userId);
}
