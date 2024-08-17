package kraynov.n.financialaccountingsystembackend.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import kraynov.n.financialaccountingsystembackend.dao.CurrencyDAO;
import kraynov.n.financialaccountingsystembackend.model.CurrencyDTO;

public class CurrencyPostgresDAO implements CurrencyDAO {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbc;

    public CurrencyPostgresDAO(JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedJdbc) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbc = namedJdbc;
    }

    @Override
    public List<CurrencyDTO> getAll() {
        return jdbcTemplate.query("select * from currency", this::mapRowToCurrency);
    }

    @Override
    public CurrencyDTO getById(String id) {
        return namedJdbc.queryForObject("select * from currency where id = :id", Map.of("id", id),
                this::mapRowToCurrency);
    }

    private CurrencyDTO mapRowToCurrency(ResultSet row, int rowNum) throws SQLException {
        return CurrencyDTO.builder()
                .setId(row.getString("id"))
                .setFullName(row.getString("full_name"))
                .setShortName(row.getString("short_name"))
                .setSymbol(row.getString("symbol"))
                .setIsoCode(row.getShort("iso_code"))
                .build();
    }
}
