package kraynov.n.financialaccountingsystembackend.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleNodeImpl;

public class NodePostgresDAO implements NodeDAO {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbc;

    public NodePostgresDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbc) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbc = namedJdbc;
    }

    @Override
    public Node save(Node node) {
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
    public Node update(Node node, String userId) {
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
    public Node getById(String nodeId) {
        return namedJdbc.queryForObject(
                "select * from node_with_last_transaction_date where id = :nodeId",
                Map.of("nodeId", nodeId),
                this::mapRowToNode);
    }

    @Override
    public List<Node> getAll(String userId) {
        return namedJdbc.query(
                "select * from node_with_last_transaction_date where user_id = :userId",
                Map.of("userId", userId),
                this::mapRowToNode);
    }

    private Node mapRowToNode(ResultSet row, int rowNum) throws SQLException {
        return new SimpleNodeImpl.Builder()
                .setId(row.getString("id"))
                .setName(row.getString("name"))
                .setDescription(row.getString("description"))
                .setCurrencyId(row.getString("currencyId"))
                .setAmount(row.getBigDecimal("amount"))
                .setUserId(row.getString("user_id"))
                .setExternal(row.getBoolean("is_external"))
                .setLastTransactionDate(
                        row.getTimestamp("last_transaction_date") == null ? null :
                                LocalDate.ofInstant(row.getTimestamp("last_transaction_date").toInstant(),
                                        TimeZone.getDefault().toZoneId()))
                .setOverdraft(row.getBoolean("is_overdraft"))
                .setArchived(row.getBoolean("is_archived"))
                .build();
    }
}
