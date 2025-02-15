package kraynov.n.financialaccountingsystembackend.dao.impl;

import kraynov.n.financialaccountingsystembackend.dao.TransactionDAO;
import kraynov.n.financialaccountingsystembackend.model.TransactionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
    public TransactionDto get(String transactionId) {

        return namedJdbc.queryForObject(
                "select * from transaction_with_extended_info where id = :id",
                Map.of("id", transactionId),
                this::mapRowToTransaction);
    }

    @Override
    public List<TransactionDto> getAll(String userId) {
        logger.debug("Start retrieving all transactions by userId");
        List<TransactionDto> result = namedJdbc.query(
                "select * from transaction_with_extended_info where user_id = :userId order by timestamp desc",
                Map.of("userId", userId),
                this::mapRowToTransaction);
        logger.debug("Finish retrieving all transactions by userId");
        return result;

    }

    @Override
    public List<TransactionDto> getAllBySenderId(int id) {
        // toDo: implement method
        return null;
    }

    @Override
    public List<TransactionDto> getAllByReceiverId(int id) {
        // toDo: implement method
        return null;
    }

    @Override
    public List<TransactionDto> getAllByNodeId(String id) {
        return namedJdbc.query(
                "select * from transaction_with_extended_info" +
                        " where sendernodeid = :nodeId or receivernodeid = :nodeId order by timestamp desc",
                Map.of("nodeId", id), this::mapRowToTransaction);
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
                .isFromExternal(row.getBoolean("is_from_external"))
                .isToExternal(row.getBoolean("is_to_external"))
                .senderName(row.getString("sender_name"))
                .senderCurrencyId(row.getString("sender_currency_id"))
                .receiverName(row.getString("receiver_name"))
                .receiverCurrencyId(row.getString("receiver_currency_id"))
                .build();
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

}
