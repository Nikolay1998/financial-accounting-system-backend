package kraynov.n.financialaccountingsystembackend.dao.impl;

import kraynov.n.financialaccountingsystembackend.dao.UserDAO;
import kraynov.n.financialaccountingsystembackend.dto.UserDetailsDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserPostgresDAO implements UserDAO {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbc;

    public UserPostgresDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbc) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbc = namedJdbc;
    }

    @Override
    public UserDetailsDto getByName(String username) {
        try {
            return namedJdbc.queryForObject("select * from fas_user where name = :username", Map.of("username", username), this::mapRowToUser);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public UserDetailsDto save(UserDetailsDto userDTO) {
        jdbcTemplate.update("insert into fas_user values (?, ?, ?)",
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getPassword());
        return userDTO;
    }

    private UserDetailsDto mapRowToUser(ResultSet row, int rowNum) throws SQLException {
        return new UserDetailsDto(row.getString("id"), row.getString("name"), row.getString("password"));
    }
}
