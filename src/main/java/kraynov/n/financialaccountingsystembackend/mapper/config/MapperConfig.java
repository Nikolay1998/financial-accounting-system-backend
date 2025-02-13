package kraynov.n.financialaccountingsystembackend.mapper.config;

import kraynov.n.financialaccountingsystembackend.mapper.NodeMapper;
import kraynov.n.financialaccountingsystembackend.mapper.TransactionMapper;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public TransactionMapper transactionMapper(CurrencyService simpleCurrencyService) {
        return new TransactionMapper(simpleCurrencyService);
    }

    @Bean
    public NodeMapper nodeMapper(CurrencyService simpleCurrencyService) {
        return new NodeMapper(simpleCurrencyService);
    }
}
