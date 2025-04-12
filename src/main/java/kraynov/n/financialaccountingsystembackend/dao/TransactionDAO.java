package kraynov.n.financialaccountingsystembackend.dao;

import kraynov.n.financialaccountingsystembackend.dto.TransactionDto;

import java.sql.BatchUpdateException;
import java.util.List;

public interface TransactionDAO {

    TransactionDto get(String transactionId);

    TransactionDto save(TransactionDto transaction);

    TransactionDto update(TransactionDto transaction, String userId);

    List<TransactionDto> getAllByIds(List<String> ids);

    List<TransactionDto> batchUpdate(List<TransactionDto> transactions) throws BatchUpdateException;

}
