package kraynov.n.financialaccountingsystembackend.dao.config;

import kraynov.n.financialaccountingsystembackend.dao.*;
import kraynov.n.financialaccountingsystembackend.dao.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DaoConfiguration {
    @Bean
    public NodeDao nodePostgresDao(
            JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedJdbc
    ) {
        return new NodePostgresDao(jdbcTemplate, namedJdbc);
    }

    @Bean
    public TransactionExtendedInfoDao transactionPostgresDao(NamedParameterJdbcTemplate namedJdbc) {
        return new TransactionPostgresExtendedInfoDao(namedJdbc);
    }

    @Bean
    public TransactionDao transactionFullInfoPostgresDao(NamedParameterJdbcTemplate namedJdbc) {
        return new TransactionPostgresDao(namedJdbc);
    }

    @Bean
    public UserDao userPostgresDao(
            JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedJdbc
    ) {
        return new UserPostgresDao(jdbcTemplate, namedJdbc);
    }

    @Bean
    public CurrencyDao currencyPostgresDao(
            JdbcTemplate jdbcTemplate,
            NamedParameterJdbcTemplate namedJdbc
    ) {
        return new CurrencyPostgresDao(jdbcTemplate, namedJdbc);
    }

    @Bean
    public CurrencyDao currencyCachedDao(CurrencyDao currencyPostgresDao) {
        return new CurrencyCachedDao(currencyPostgresDao);
    }
}
