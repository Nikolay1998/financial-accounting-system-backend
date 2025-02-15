package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.exception.AlreadyCanceledException;
import kraynov.n.financialaccountingsystembackend.model.TransactionDto;
import kraynov.n.financialaccountingsystembackend.model.TransactionExtendedInfoDto;

import java.util.List;

public interface TransactionService {
    TransactionExtendedInfoDto add(TransactionDto transaction);

    TransactionDto get(String id);

    TransactionExtendedInfoDto getExtendedInfo(String id);

    List<TransactionExtendedInfoDto> getAll();

    List<TransactionExtendedInfoDto> getAllBySenderId(int id);

    List<TransactionExtendedInfoDto> getAllByReceiverId(int id);

    List<TransactionExtendedInfoDto> getAllByNodeId(String id);

    TransactionDto cancel(String transactionId) throws AlreadyCanceledException;

    TransactionExtendedInfoDto edit(TransactionDto transaction);

    TransactionDto restore(String transactionId);
}
