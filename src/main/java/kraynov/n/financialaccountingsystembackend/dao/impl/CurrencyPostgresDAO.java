package kraynov.n.financialaccountingsystembackend.dao.impl;

import kraynov.n.financialaccountingsystembackend.dao.CurrencyDAO;
import kraynov.n.financialaccountingsystembackend.dto.CurrencyDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CurrencyPostgresDAO implements CurrencyDAO {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbc;

    public CurrencyPostgresDAO(JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedJdbc) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbc = namedJdbc;
    }

    @Override
    public List<CurrencyDto> getAll() {
        return jdbcTemplate.query("select * from currency", this::mapRowToCurrency);
    }

    @Override
    public CurrencyDto getById(String id) {
        return namedJdbc.queryForObject("select * from currency where id = :id", Map.of("id", id),
                this::mapRowToCurrency);
    }

    private CurrencyDto mapRowToCurrency(ResultSet row, int rowNum) throws SQLException {
        return CurrencyDto.builder()
                .id(row.getString("id"))
                .fullName(row.getString("full_name"))
                .shortName(row.getString("short_name"))
                .symbol(row.getString("symbol"))
                .isoCode(row.getShort("iso_code"))
                .build();
    }
}
