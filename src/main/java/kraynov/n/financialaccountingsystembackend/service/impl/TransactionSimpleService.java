package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.TransactionDAO;
import kraynov.n.financialaccountingsystembackend.model.Transaction;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;

import java.util.List;

public class TransactionSimpleService implements TransactionService {

    private final TransactionDAO transactionDAO;


    public TransactionSimpleService(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @Override
    public Transaction add(Transaction transaction) {
        //toDO: calculate amounts after transaction
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
