package kraynov.n.financialaccountingsystembackend.dao;

import kraynov.n.financialaccountingsystembackend.dto.TransactionExtendedInfoDto;

import java.util.List;

public interface TransactionExtendedInfoDAO {

    TransactionExtendedInfoDto get(String transactionId);

    List<TransactionExtendedInfoDto> getAllByUserId(String usedId);

    List<TransactionExtendedInfoDto> getAllBySenderId(int id);

    List<TransactionExtendedInfoDto> getAllByReceiverId(int id);

    List<TransactionExtendedInfoDto> getAllByNodeId(String id);

    List<TransactionExtendedInfoDto> getAllByIds(List<String> ids);
}
