package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;
import kraynov.n.financialaccountingsystembackend.model.NodeDto;
import kraynov.n.financialaccountingsystembackend.model.NodeExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.model.TransactionDto;

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
