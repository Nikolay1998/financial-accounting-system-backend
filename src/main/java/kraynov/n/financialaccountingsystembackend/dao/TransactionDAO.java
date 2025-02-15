package kraynov.n.financialaccountingsystembackend.dao;

import kraynov.n.financialaccountingsystembackend.model.TransactionDto;

public interface TransactionDAO {

    TransactionDto get(String transactionId);

    TransactionDto save(TransactionDto transaction);

    TransactionDto update(TransactionDto transaction, String userId);
}
