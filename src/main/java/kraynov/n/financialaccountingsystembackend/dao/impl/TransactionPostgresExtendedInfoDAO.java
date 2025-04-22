package kraynov.n.financialaccountingsystembackend.dao.impl;

import kraynov.n.financialaccountingsystembackend.dao.TransactionExtendedInfoDAO;
import kraynov.n.financialaccountingsystembackend.dto.TransactionExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.dto.TransactionFilterDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class TransactionPostgresExtendedInfoDAO implements TransactionExtendedInfoDAO {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final NamedParameterJdbcTemplate namedJdbc;

    public TransactionPostgresExtendedInfoDAO(NamedParameterJdbcTemplate namedJdbc) {
        this.namedJdbc = namedJdbc;
    }

    @Override
    public TransactionExtendedInfoDto get(String transactionId) {

        return namedJdbc.queryForObject(
                "select * from transaction_with_extended_info where id = :id",
                Map.of("id", transactionId),
                this::mapRowToTransaction);
    }

    @Override
    public List<TransactionExtendedInfoDto> getAllByUserId(String userId) {
        logger.debug("Start retrieving all transactions by userId");
        List<TransactionExtendedInfoDto> result = namedJdbc.query(
                "select * from transaction_with_extended_info where user_id = :userId order by timestamp desc, order_number desc",
                Map.of("userId", userId),
                this::mapRowToTransaction);
        logger.debug("Finish retrieving all transactions by userId");
        return result;

    }

    @Override
    public List<TransactionExtendedInfoDto> getAllBySenderId(int id) {
        // toDo: implement method
        return null;
    }

    @Override
    public List<TransactionExtendedInfoDto> getAllByReceiverId(int id) {
        // toDo: implement method
        return null;
    }

    @Override
    public List<TransactionExtendedInfoDto> getAllByNodeId(String id) {
        return namedJdbc.query(
                "select * from transaction_with_extended_info" +
                        " where sendernodeid = :nodeId or receivernodeid = :nodeId order by timestamp desc",
                Map.of("nodeId", id), this::mapRowToTransaction);
    }

    @Override
    public List<TransactionExtendedInfoDto> getAllByIds(List<String> ids) {
        return namedJdbc.query(
                """
                        SELECT * FROM transaction_with_extended_info WHERE id IN (:ids)
                        """,
                Map.of("ids", ids),
                this::mapRowToTransaction);
    }

    @Override
    public List<TransactionExtendedInfoDto> getAllByFilter(TransactionFilterDto filter, String userId) {
        return namedJdbc.query(
                """
                        SELECT * FROM transaction_with_extended_info
                                 WHERE user_id = :userId
                                 AND DATE("timestamp") between :from and :to
                                 order by timestamp desc, order_number desc
                        """,
                Map.of("userId", userId,
                        "from", filter.getFrom().atStartOfDay(),
                        "to", filter.getTo().plusDays(1).atStartOfDay()),
                this::mapRowToTransaction);
    }

    private TransactionExtendedInfoDto mapRowToTransaction(ResultSet row, int rowNum) throws SQLException {
        return TransactionExtendedInfoDto.builder()
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
                .order(row.getInt("order_number"))
                .build();
    }

}
