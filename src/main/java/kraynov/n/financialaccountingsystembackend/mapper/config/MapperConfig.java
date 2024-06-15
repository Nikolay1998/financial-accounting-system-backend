package kraynov.n.financialaccountingsystembackend.mapper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kraynov.n.financialaccountingsystembackend.mapper.NodeMapper;
import kraynov.n.financialaccountingsystembackend.mapper.TransactionMapper;
import kraynov.n.financialaccountingsystembackend.service.NodeService;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;

@Configuration
public class MapperConfig {
    @Bean
    public TransactionMapper transactionMapper(NodeService simpleNodeService) {
        return new TransactionMapper(simpleNodeService);
    }

    @Bean
    public NodeMapper nodeMapper(TransactionService simpleTransactionService) {
        return new NodeMapper(simpleTransactionService);
    }
}
