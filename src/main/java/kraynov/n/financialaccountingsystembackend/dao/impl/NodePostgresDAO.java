package kraynov.n.financialaccountingsystembackend.dao.impl;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleNodeImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
                "insert into node values (?, ?, ?, ?, ?, ?, ?)",
                node.getId(),
                node.getName(),
                node.getDescription(),
                node.getCurrencyId(),
                node.getAmount(),
                node.isExternal(),
                node.getUserId());
        return node;
    }

    public Node update(Node node) {
        namedJdbc.update(
                """
                        update node
                        set name = :name,
                        description = :description,
                        currencyid = :currencyid,
                        amount = :amount,
                        user_id = :user_id,
                        is_external = :is_external
                        where id = :id
                        """
                ,
                Map.of("name", node.getName(),
                        "description", node.getDescription(),
                        "currencyid", node.getCurrencyId(),
                        "amount", node.getAmount(),
                        "user_id", node.getUserId(),
                        "is_external", node.isExternal(),
                        "id", node.getId())
        );
        return node;
    }

    @Override
    public Node getById(String nodeId) {
        return namedJdbc.queryForObject(
                "select * from node where id = :nodeId",
                Map.of("nodeId", nodeId),
                this::mapRowToNode);
    }

    @Override
    public List<Node> getAll(String userId) {
        return namedJdbc.query("select * from node where user_id = :userId", Map.of("userId", userId), this::mapRowToNode);
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
                .build();
    }
}
