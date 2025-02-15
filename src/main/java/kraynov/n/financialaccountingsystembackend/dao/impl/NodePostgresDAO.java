package kraynov.n.financialaccountingsystembackend.dao.impl;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.model.NodeDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class NodePostgresDAO implements NodeDAO {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbc;

    public NodePostgresDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbc) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbc = namedJdbc;
    }

    @Override
    public NodeDto save(NodeDto node) {
        jdbcTemplate.update(
                "insert into node values (?, ?, ?, ?, ?, ?, ?, ?, ?)",
                node.getId(),
                node.getName(),
                node.getDescription(),
                node.getCurrencyId(),
                node.getAmount(),
                node.isExternal(),
                node.getUserId(),
                node.isOverdraft(),
                node.isArchived()
        );
        return node;
    }

    @Override
    public NodeDto update(NodeDto node, String userId) {
        int updated = namedJdbc.update(
                """
                        update node
                        set name = :name,
                        description = :description,
                        currencyid = :currencyid,
                        amount = :amount,
                        is_external = :is_external,
                        is_overdraft = :is_overdraft,
                        is_archived = :is_archived
                        where id = :id and user_id = :user_id
                        """,
                Map.of("name", node.getName(),
                        "description", node.getDescription(),
                        "currencyid", node.getCurrencyId(),
                        "amount", node.getAmount(),
                        "user_id", userId,
                        "is_external", node.isExternal(),
                        "is_overdraft", node.isOverdraft(),
                        "is_archived", node.isArchived(),
                        "id", node.getId()));
        if (updated > 0) {
            return node;
        }
        return null;
    }

    @Override
    public NodeDto getById(String nodeId) {
        return namedJdbc.queryForObject(
                "select * from node_with_last_transaction_date where id = :nodeId",
                Map.of("nodeId", nodeId),
                this::mapRowToNode);
    }

    @Override
    public List<NodeDto> getAll(String userId) {
        return namedJdbc.query(
                "select * from node_with_last_transaction_date where user_id = :userId",
                Map.of("userId", userId),
                this::mapRowToNode);
    }

    private NodeDto mapRowToNode(ResultSet row, int rowNum) throws SQLException {
        return NodeDto.builder()
                .id(row.getString("id"))
                .name(row.getString("name"))
                .description(row.getString("description"))
                .currencyId(row.getString("currencyId"))
                .amount(row.getBigDecimal("amount"))
                .userId(row.getString("user_id"))
                .isExternal(row.getBoolean("is_external"))
                .lastTransactionDate(
                        row.getTimestamp("last_transaction_date") == null ? null :
                                LocalDate.ofInstant(row.getTimestamp("last_transaction_date").toInstant(),
                                        TimeZone.getDefault().toZoneId()))
                .isOverdraft(row.getBoolean("is_overdraft"))
                .isArchived(row.getBoolean("is_archived"))
                .build();
    }
}
