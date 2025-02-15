package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.exception.AlreadyCanceledException;
import kraynov.n.financialaccountingsystembackend.model.TransactionDto;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    TransactionDto add(TransactionDto transaction);

    TransactionDto get(String id);

    List<TransactionDto> getAll();

    List<TransactionDto> getAllBySenderId(int id);

    List<TransactionDto> getAllByReceiverId(int id);

    List<TransactionDto> getAllByNodeId(String id);

    Optional<TransactionDto> getLastTransactionByNodeId(String id);

    TransactionDto cancel(String transactionId) throws AlreadyCanceledException;

    TransactionDto edit(TransactionDto transaction);

    TransactionDto restore(String transactionId);
}
