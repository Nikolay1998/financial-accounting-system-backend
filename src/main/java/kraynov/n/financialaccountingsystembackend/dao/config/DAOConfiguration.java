package kraynov.n.financialaccountingsystembackend.dao.config;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.dao.impl.NodePostgresDAO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DAOConfiguration {
    @Bean
    public NodeDAO nodePostgresDAO(JdbcTemplate jdbcTemplate) {
        return new NodePostgresDAO(jdbcTemplate);
    }
}
