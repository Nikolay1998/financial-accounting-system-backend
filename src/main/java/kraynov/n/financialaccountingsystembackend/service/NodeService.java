package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.dto.NodeDto;
import kraynov.n.financialaccountingsystembackend.dto.NodeExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.dto.TransactionDto;
import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;

import java.util.List;

public interface NodeService {
    NodeExtendedInfoDto add(NodeDto node);

    NodeExtendedInfoDto edit(NodeDto node);

    NodeExtendedInfoDto get(String id);

    List<NodeExtendedInfoDto> getAll();

    NodeExtendedInfoDto archive(String id);

    NodeExtendedInfoDto restore(String id);

    void calculateTransactionAffection(TransactionDto transaction) throws InsufficientFundsException;

    void cancelTransactionAffection(TransactionDto transaction) throws InsufficientFundsException;
}
