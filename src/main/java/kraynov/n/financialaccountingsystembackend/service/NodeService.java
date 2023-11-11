package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;
import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.model.Transaction;

import java.util.List;

public interface NodeService {
    Node add(Node node);

    List<Node> getAll();

    void calculateTransactionAffection(Transaction transaction) throws InsufficientFundsException;

    void cancelTransactionAffection(Transaction transaction) throws InsufficientFundsException;
}
