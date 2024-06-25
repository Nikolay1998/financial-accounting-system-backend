package kraynov.n.financialaccountingsystembackend.service;

import java.util.List;

import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;
import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.model.Transaction;

public interface NodeService {
    Node add(Node node);

    Node get(String id);

    List<Node> getAll();

    void calculateTransactionAffection(Transaction transaction) throws InsufficientFundsException;

    void cancelTransactionAffection(Transaction transaction) throws InsufficientFundsException;
}
