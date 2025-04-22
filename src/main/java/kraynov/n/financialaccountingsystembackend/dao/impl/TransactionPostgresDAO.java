package kraynov.n.financialaccountingsystembackend.dao.impl;

import kraynov.n.financialaccountingsystembackend.dao.TransactionDAO;
import kraynov.n.financialaccountingsystembackend.dto.TransactionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;

import java.sql.BatchUpdateException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
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

    @Override
    public List<TransactionDto> getAllByIds(List<String> ids) {
        return namedJdbc.query(
                """
                        SELECT * FROM transaction WHERE id IN (:ids)
                        """,
                Map.of("ids", ids),
                this::mapRowToTransaction);
    }

    @Override
    public List<TransactionDto> batchUpdate(List<TransactionDto> transactions) throws BatchUpdateException {
        int[] updated = namedJdbc.batchUpdate("""
                        update transaction
                        set sendernodeid = :senderNodeId,
                        receivernodeid = :receiverNodeId,
                        description = :description,
                        senderamount = :senderAmount,
                        receiveramount = :receiverAmount,
                        timestamp = :date,
                        is_cancelled = :cancelled,
                        order_number = :order
                        where id = :id and user_id = :userId
                        """,
                SqlParameterSourceUtils.createBatch(transactions));
        if (Arrays.stream(updated).anyMatch(row -> row != 1)) {
            throw new BatchUpdateException("Something went wrong", updated);
        }
        return transactions;
    }


//    @Override
//    public List<TransactionDto> swap(String firstTransactionId, String secondTransactionId) {
//        int updated = namedJdbc.update(
//                """
//                        UPDATE transaction
//                        SET
//                            order_number = CASE
//                                WHEN id = :firstTransactionId THEN (SELECT order_number FROM transaction WHERE id = :secondTransactionId)
//                                WHEN id = :secondTransactionId THEN (SELECT order_number FROM transaction WHERE id = :firstTransactionId)
//                            END
//                        WHERE
//                            id IN (:firstTransactionId, :secondTransactionId);
//                        """,
//                Map.of("firstTransactionId", firstTransactionId,
//                        "secondTransactionId", secondTransactionId)
//        );
//        );
//    }

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
                .order(row.getInt("order_number"))
                .userId(row.getString("user_id"))
                .build();
    }
}
