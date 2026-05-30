package kraynov.n.financialaccountingsystembackend.service.config;

import kraynov.n.financialaccountingsystembackend.client.RateClient;
import kraynov.n.financialaccountingsystembackend.dao.*;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.*;
import kraynov.n.financialaccountingsystembackend.service.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {
    @Bean
    public NodeService simpleNodeService(
            NodeDao nodeDao,
            ContextHolderFacade contextHolderFacade
    ) {
        return new NodeSimpleService(nodeDao, contextHolderFacade);
    }

    @Bean
    public TransactionService simpleTransactionService(
            TransactionExtendedInfoDao transactionExtendedInfoDao,
            ContextHolderFacade contextHolderFacade,
            TransactionDao transactionDao
    ) {
        return new TransactionSimpleService(transactionExtendedInfoDao, transactionDao, contextHolderFacade);
    }

    @Bean
    public SummaryService simpleSummaryService(
            NodeDao nodeDao,
            ContextHolderFacade contextHolderFacade,
            TransactionService transactionService,
            CurrencyService simpleCurrencyService
    ) {
        return new SummarySimpleService(nodeDao, transactionService, contextHolderFacade, simpleCurrencyService);
    }

    @Bean
    public UserService simpleUserService(
            UserDao userDao,
            ContextHolderFacade contextHolderFacade
    ) {
        return new UserSimpleService(userDao, contextHolderFacade);
    }

    @Bean
    public FasFacade fasFacade(
            TransactionService simpleTransactionService,
            NodeService simpleNodeService
    ) {
        return new FasSimpleFacade(simpleNodeService, simpleTransactionService);
    }

    @Bean
    public CurrencyService simpleCurrencyService(
            CurrencyDao currencyCachedDao
    ) {
        return new CurrencySimpleService(currencyCachedDao);
    }

    @Bean
    public RateService rateService(
            RateClient rateClient,
            NodeService nodeService,
            ContextHolderFacade contextHolderFacade
    ) {
        return new RateServiceImpl(rateClient, nodeService, contextHolderFacade);
    }
}