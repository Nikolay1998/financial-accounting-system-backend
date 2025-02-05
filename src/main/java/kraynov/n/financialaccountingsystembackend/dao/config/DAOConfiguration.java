package kraynov.n.financialaccountingsystembackend.dao.config;

import kraynov.n.financialaccountingsystembackend.dao.CurrencyDAO;
import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.dao.TransactionDAO;
import kraynov.n.financialaccountingsystembackend.dao.UserDAO;
import kraynov.n.financialaccountingsystembackend.dao.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DAOConfiguration {
    @Bean
    public NodeDAO nodePostgresDAO(JdbcTemplate jdbcTemplate,
                                   NamedParameterJdbcTemplate namedJdbc) {
        return new NodePostgresDAO(jdbcTemplate, namedJdbc);
    }

    @Bean
    public TransactionDAO transactionPostgresDAO(NamedParameterJdbcTemplate namedJdbc) {
        return new TransactionPostgresDAO(namedJdbc);
    }

    @Bean
    public UserDAO userPostgresDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedJdbc) {
        return new UserPostgresDAO(jdbcTemplate, namedJdbc);
    }

    @Bean
    public CurrencyDAO currencyPosgresDAO(
            JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedJdbc) {
        return new CurrencyPostgresDAO(jdbcTemplate, namedJdbc);
    }

    @Bean
    public CurrencyDAO currencyCachedDAO(CurrencyDAO currencyPosgresDAO) {
        return new CurrencyCachedDAO(currencyPosgresDAO);
    }
}
