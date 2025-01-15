package kraynov.n.financialaccountingsystembackend.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import kraynov.n.financialaccountingsystembackend.dao.TransactionDAO;
import kraynov.n.financialaccountingsystembackend.model.Transaction;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleTransactionImpl;

public class TransactionPostgresDAO implements TransactionDAO {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final NamedParameterJdbcTemplate namedJdbc;

    public TransactionPostgresDAO(NamedParameterJdbcTemplate namedJdbc) {
        this.namedJdbc = namedJdbc;
    }

    @Override
    public Transaction save(Transaction transaction) {
        namedJdbc.update(
                "insert into transaction values (:id, :senderNodeId, :receiverNodeId, :description, :senderAmount, :receiverAmount, :dateTime, :userId, :isCancelled)",
                Map.of(
                        "id", transaction.getId(),
                        "senderNodeId", transaction.getSenderNodeId(),
                        "receiverNodeId", transaction.getReceiverNodeId(),
                        "description", transaction.getDescription(),
                        "senderAmount", transaction.getSenderAmount(),
                        "receiverAmount", transaction.getReceiverAmount(),
                        "dateTime", java.sql.Date.valueOf((transaction.getDateTime())),
                        "isCancelled", transaction.isCancelled(),
                        "userId", transaction.getUserId()));
        return transaction;
    }

    @Override
    public Transaction get(String transactionId) {

        return namedJdbc.queryForObject(
                "select * from transaction where id = :id",
                Map.of("id", transactionId),
                this::mapRowToTransaction);
    }

    @Override
    public List<Transaction> getAll(String userId) {
        return namedJdbc.query(
                "select * from transaction where user_id = :userId order by timestamp desc",
                Map.of("userId", userId),
                this::mapRowToTransaction);

    }

    @Override
    public List<Transaction> getAllBySenderId(int id) {
        // toDo: implement method
        return null;
    }

    @Override
    public List<Transaction> getAllByReceiverId(int id) {
        // toDo: implement method
        return null;
    }

    @Override
    public List<Transaction> getAllByNodeId(String id) {
        return namedJdbc.query(
                "select * from transaction where sendernodeid = :nodeId or receivernodeid = :nodeId order by timestamp desc",
                Map.of("nodeId", id), this::mapRowToTransaction);
    }

    private Transaction mapRowToTransaction(ResultSet row, int rowNum) throws SQLException {
        return SimpleTransactionImpl.builder()
                .setId(row.getString("id"))
                .setDescription(row.getString("description"))
                .setSenderNodeId(row.getString("senderNodeId"))
                .setReceiverNodeId(row.getString("receiverNodeId"))
                .setSenderAmount(row.getBigDecimal("senderamount"))
                .setReceiverAmount(row.getBigDecimal("receiveramount"))
                .setTime(LocalDate.ofInstant(row.getTimestamp("timestamp").toInstant(),
                        TimeZone.getDefault().toZoneId()))
                .setCancelled(row.getBoolean("is_cancelled"))
                .setUserId(row.getString("user_id"))
                .build();
    }

    @Override
    public Transaction update(Transaction transaction, String userId) {
        logger.debug("Start updating transaction: {}", transaction);

        int updated = namedJdbc.update(
                """
                        update transaction
                        set sendernodeid = :senderNodeId,
                        receivernodeid = :receiverNodeId,
                        description = :description,
                        senderamount = :senderAmount,
                        receiveramount = :receiverAmount,
                        timestamp = :dateTime,
                        is_cancelled = :isCancelled
                        where id = :id and user_id = :userId
                        """,
                Map.of(
                        "id", transaction.getId(),
                        "senderNodeId", transaction.getSenderNodeId(),
                        "receiverNodeId", transaction.getReceiverNodeId(),
                        "description", transaction.getDescription(),
                        "senderAmount", transaction.getSenderAmount(),
                        "receiverAmount", transaction.getReceiverAmount(),
                        "dateTime", java.sql.Date.valueOf((transaction.getDateTime())),
                        "isCancelled", transaction.isCancelled(),
                        "userId", userId));

        if (updated > 0) {
            return transaction;
        }
        return null;
    }

}
