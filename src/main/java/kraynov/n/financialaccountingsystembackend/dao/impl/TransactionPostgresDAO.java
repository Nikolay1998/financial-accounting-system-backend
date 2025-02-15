package kraynov.n.financialaccountingsystembackend.dao.impl;

import kraynov.n.financialaccountingsystembackend.dao.TransactionDAO;
import kraynov.n.financialaccountingsystembackend.model.TransactionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Map;
import java.util.TimeZone;

public class TransactionPostgresDAO implements TransactionDAO {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final NamedParameterJdbcTemplate namedJdbc;

    public TransactionPostgresDAO(NamedParameterJdbcTemplate namedJdbc) {
        this.namedJdbc = namedJdbc;
    }

    @Override
    public TransactionDto get(String transactionId) {
        return namedJdbc.queryForObject(
                "select * from transaction_with_extended_info where id = :id",
                Map.of("id", transactionId),
                this::mapRowToTransaction);
    }

    @Override
    public TransactionDto save(TransactionDto transaction) {
        namedJdbc.update(
                "insert into transaction values (:id, :senderNodeId, :receiverNodeId, :description, :senderAmount, :receiverAmount, :dateTime, :userId, :isCancelled)",
                Map.of(
                        "id", transaction.getId(),
                        "senderNodeId", transaction.getSenderNodeId(),
                        "receiverNodeId", transaction.getReceiverNodeId(),
                        "description", transaction.getDescription(),
                        "senderAmount", transaction.getSenderAmount(),
                        "receiverAmount", transaction.getReceiverAmount(),
                        "dateTime", java.sql.Date.valueOf((transaction.getDate())),
                        "isCancelled", transaction.isCancelled(),
                        "userId", transaction.getUserId()));
        return transaction;
    }

    @Override
    public TransactionDto update(TransactionDto transaction, String userId) {
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
                        "dateTime", java.sql.Date.valueOf((transaction.getDate())),
                        "isCancelled", transaction.isCancelled(),
                        "userId", userId));

        if (updated > 0) {
            return transaction;
        }
        return null;
    }

    private TransactionDto mapRowToTransaction(ResultSet row, int rowNum) throws SQLException {
        return TransactionDto.builder()
                .id(row.getString("id"))
                .description(row.getString("description"))
                .senderNodeId(row.getString("senderNodeId"))
                .receiverNodeId(row.getString("receiverNodeId"))
                .senderAmount(row.getBigDecimal("senderamount"))
                .receiverAmount(row.getBigDecimal("receiveramount"))
                .date(LocalDate.ofInstant(row.getTimestamp("timestamp").toInstant(),
                        TimeZone.getDefault().toZoneId()))
                .isCancelled(row.getBoolean("is_cancelled"))
                .userId(row.getString("user_id"))
                .build();
    }
}
