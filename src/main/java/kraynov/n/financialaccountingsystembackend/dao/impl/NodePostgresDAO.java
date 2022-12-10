package kraynov.n.financialaccountingsystembackend.dao.impl;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleNodeImpl;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NodePostgresDAO implements NodeDAO {
    private final JdbcTemplate jdbcTemplate;

    public NodePostgresDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Node save(Node node) {
        jdbcTemplate.update(
                "insert into node values (?, ?, ?, ?, ?, ?)",
                node.getId(),
                node.getName(),
                node.getDescription(),
                node.getCurrencyId(),
                node.getAmount(),
                node.isExternal());
        return node;
    }

    @Override
    public List<Node> getAll() {
        return jdbcTemplate.query("select * from node", this::mapRowToNode);
    }

    private Node mapRowToNode(ResultSet row, int rowNum) throws SQLException {
        return new SimpleNodeImpl(row.getInt("id"),
                row.getString("name"),
                row.getString("description"),
                row.getInt("currencyId"),
                row.getBigDecimal("amount"),
                row.getBoolean("is_external"));
    }
}
