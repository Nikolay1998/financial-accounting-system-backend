package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    Transaction add(Transaction transaction);

    Transaction get(String id);

    List<Transaction> getAll();

    List<Transaction> getAllBySenderId(int id);

    List<Transaction> getAllByReceiverId(int id);

    List<Transaction> getAllByNodeId(String id);

    Optional<Transaction> getLastTransactionByNodeId(String id);

    Transaction cancel(String transactionId);
}
