package kraynov.n.financialaccountingsystembackend.dao.impl;

import kraynov.n.financialaccountingsystembackend.dao.TransactionDAO;
import kraynov.n.financialaccountingsystembackend.model.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class TransactionPostgresDAO implements TransactionDAO {

    private final JdbcTemplate jdbcTemplate;

    public TransactionPostgresDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transaction save(Transaction transaction) {
        jdbcTemplate.update(
                "insert into transaction values (?, ?, ?, ?, ?, ?)",
                transaction.getId(),
                transaction.getDescription(),
                transaction.getSenderNodeId(),
                transaction.getReceiverNodeId(),
                transaction.getSenderAmount(),
                transaction.getReceiverAmount());
        return transaction;
    }

    @Override
    public List<Transaction> getAll() {
        //toDo: implement method
        return null;
    }

    @Override
    public List<Transaction> getAllBySenderId(int id) {
        //toDo: implement method
        return null;
    }

    @Override
    public List<Transaction> getAllByReceiverId(int id) {
        //toDo: implement method
        return null;
    }
}
