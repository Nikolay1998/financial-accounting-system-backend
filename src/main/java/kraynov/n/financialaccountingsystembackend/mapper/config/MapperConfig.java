package kraynov.n.financialaccountingsystembackend.mapper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kraynov.n.financialaccountingsystembackend.mapper.NodeMapper;
import kraynov.n.financialaccountingsystembackend.mapper.TransactionMapper;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;
import kraynov.n.financialaccountingsystembackend.service.NodeService;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;

@Configuration
public class MapperConfig {
    @Bean
    public TransactionMapper transactionMapper(NodeService simpleNodeService,
            CurrencyService simpleCurrencyService) {
        return new TransactionMapper(simpleNodeService, simpleCurrencyService);
    }

    @Bean
    public NodeMapper nodeMapper(TransactionService simpleTransactionService, CurrencyService simpleCurrencyService) {
        return new NodeMapper(simpleTransactionService, simpleCurrencyService);
    }
}
