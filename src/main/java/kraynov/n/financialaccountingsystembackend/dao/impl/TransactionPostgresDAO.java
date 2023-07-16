package kraynov.n.financialaccountingsystembackend.dao.impl;

import kraynov.n.financialaccountingsystembackend.dao.TransactionDAO;
import kraynov.n.financialaccountingsystembackend.model.Transaction;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleTransactionImpl;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.TimeZone;

public class TransactionPostgresDAO implements TransactionDAO {

    private final JdbcTemplate jdbcTemplate;

    public TransactionPostgresDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Transaction save(Transaction transaction) {
        jdbcTemplate.update(
                "insert into transaction values (?, ?, ?, ?, ?, ?, ?)",
                transaction.getId(),
                transaction.getSenderNodeId(),
                transaction.getReceiverNodeId(),
                transaction.getDescription(),
                transaction.getSenderAmount(),
                transaction.getReceiverAmount(),
                java.sql.Date.valueOf((transaction.getDateTime())));
        return transaction;
    }

    @Override
    public List<Transaction> getAll() {
        return jdbcTemplate.query("select * from transaction", this::mapRowToTransaction);
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

    private Transaction mapRowToTransaction(ResultSet row, int rowNum) throws SQLException {
        return new SimpleTransactionImpl.Builder()
                .setId(row.getString("id"))
                .setDescription(row.getString("description"))
                .setSenderNodeId(row.getString("senderNodeId"))
                .setReceiverNodeId(row.getString("receiverNodeId"))
                .setSenderAmount(row.getBigDecimal("senderamount"))
                .setReceiverAmount(row.getBigDecimal("receiveramount"))
                .setTime(LocalDate.ofInstant(row.getTimestamp("timestamp").toInstant(),
                        TimeZone.getDefault().toZoneId()))
                .build();
    }
}
