package kraynov.n.financialaccountingsystembackend.dao;

import kraynov.n.financialaccountingsystembackend.dto.TransactionDto;

public interface TransactionDAO {

    TransactionDto get(String transactionId);

    TransactionDto save(TransactionDto transaction);

    TransactionDto update(TransactionDto transaction, String userId);
}
