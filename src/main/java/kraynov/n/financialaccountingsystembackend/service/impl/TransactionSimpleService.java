package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.dao.TransactionDAO;
import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.model.Transaction;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleNodeImpl;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;

import java.util.List;

public class TransactionSimpleService implements TransactionService {

    private final TransactionDAO transactionDAO;
    private final NodeDAO nodeDAO;


    public TransactionSimpleService(TransactionDAO transactionDAO, NodeDAO nodeDAO) {
        this.transactionDAO = transactionDAO;
        this.nodeDAO = nodeDAO;
    }

    @Override
    public Transaction add(Transaction transaction) {
        Node senderNode = nodeDAO.getById(transaction.getSenderNodeId());
        Node receiverNode = nodeDAO.getById(transaction.getReceiverNodeId());
        Node newSenderNode = new SimpleNodeImpl.Builder()
                .from(senderNode)
                .setAmount(senderNode.getAmount().subtract(transaction.getSenderAmount()))
                .build();

        Node newReceiverNode = new SimpleNodeImpl.Builder()
                .from(receiverNode)
                .setAmount(receiverNode.getAmount().add(transaction.getReceiverAmount()))
                .build();

        //toDO: atomic save?
        nodeDAO.update(newSenderNode);
        nodeDAO.update(newReceiverNode);
        return transactionDAO.save(transaction);
    }

    @Override
    public List<Transaction> getAll() {
        return transactionDAO.getAll();
    }

    @Override
    public List<Transaction> getAllBySenderId(int id) {
        return transactionDAO.getAllBySenderId(id);
    }

    @Override
    public List<Transaction> getAllByReceiverId(int id) {
        return transactionDAO.getAllByReceiverId(id);
    }
}
