package kraynov.n.financialaccountingsystembackend.dao.impl;

import kraynov.n.financialaccountingsystembackend.dao.UserDao;
import kraynov.n.financialaccountingsystembackend.dto.UserDetailsDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserPostgresDao implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbc;

    public UserPostgresDao(
            JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedJdbc
    ) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedJdbc = namedJdbc;
    }

    @Override
    public UserDetailsDto getByName(String username) {
        try {
            return namedJdbc.queryForObject("select * from fas_user where name = :username",
                                            Map.of("username", username),
                                            this::mapRowToUser);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public UserDetailsDto save(UserDetailsDto userDto) {
        jdbcTemplate.update("insert into fas_user values (?, ?, ?)",
                            userDto.getId(),
                            userDto.getUsername(),
                            userDto.getPassword());
        return userDto;
    }

    private UserDetailsDto mapRowToUser(
            ResultSet row,
            int rowNum
    ) throws SQLException {
        return new UserDetailsDto(row.getString("id"), row.getString("name"), row.getString("password"));
    }
}
