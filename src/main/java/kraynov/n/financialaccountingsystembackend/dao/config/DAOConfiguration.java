package kraynov.n.financialaccountingsystembackend.dao.config;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.dao.TransactionDAO;
import kraynov.n.financialaccountingsystembackend.dao.impl.NodePostgresDAO;
import kraynov.n.financialaccountingsystembackend.dao.impl.TransactionPostgresDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DAOConfiguration {
    @Bean
    public NodeDAO nodePostgresDAO(JdbcTemplate jdbcTemplate) {
        return new NodePostgresDAO(jdbcTemplate);
    }

    @Bean
    public TransactionDAO transactionPostgresDAO(JdbcTemplate jdbcTemplate) {
        return new TransactionPostgresDAO(jdbcTemplate);
    }
}
