package kraynov.n.financialaccountingsystembackend.dao;

import kraynov.n.financialaccountingsystembackend.model.TransactionExtendedInfoDto;

import java.util.List;

public interface TransactionExtendedInfoDAO {

    TransactionExtendedInfoDto get(String transactionId);

    List<TransactionExtendedInfoDto> getAll(String usedId);

    List<TransactionExtendedInfoDto> getAllBySenderId(int id);

    List<TransactionExtendedInfoDto> getAllByReceiverId(int id);

    List<TransactionExtendedInfoDto> getAllByNodeId(String id);
}
