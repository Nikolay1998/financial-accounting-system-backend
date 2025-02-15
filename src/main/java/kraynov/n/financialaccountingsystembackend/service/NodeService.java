package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;
import kraynov.n.financialaccountingsystembackend.model.NodeDto;
import kraynov.n.financialaccountingsystembackend.model.TransactionDto;

import java.util.List;

public interface NodeService {
    NodeDto add(NodeDto node);

    NodeDto edit(NodeDto node);

    NodeDto get(String id);

    List<NodeDto> getAll();

    NodeDto archive(String id);

    NodeDto restore(String id);

    void calculateTransactionAffection(TransactionDto transaction) throws InsufficientFundsException;

    void cancelTransactionAffection(TransactionDto transaction) throws InsufficientFundsException;
}
