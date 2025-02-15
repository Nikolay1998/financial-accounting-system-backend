package kraynov.n.financialaccountingsystembackend.dao;

import kraynov.n.financialaccountingsystembackend.model.TransactionDto;

import java.util.List;

public interface TransactionDAO {
    TransactionDto save(TransactionDto transaction);

    TransactionDto get(String transactionId);

    List<TransactionDto> getAll(String usedId);

    List<TransactionDto> getAllBySenderId(int id);

    List<TransactionDto> getAllByReceiverId(int id);

    List<TransactionDto> getAllByNodeId(String id);

    TransactionDto update(TransactionDto transaction, String userId);
}
