package kraynov.n.financialaccountingsystembackend.service.config;

import kraynov.n.financialaccountingsystembackend.dao.*;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.*;
import kraynov.n.financialaccountingsystembackend.service.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {
    @Bean
    public NodeService simpleNodeService(NodeDAO nodeDAO, ContextHolderFacade contextHolderFacade) {
        return new NodeSimpleService(nodeDAO, contextHolderFacade);
    }

    @Bean
    public TransactionService simpleTransactionService(TransactionExtendedInfoDAO transactionExtendedInfoDAO,
                                                       ContextHolderFacade contextHolderFacade,
                                                       TransactionDAO transactionDAO) {
        return new TransactionSimpleService(transactionExtendedInfoDAO, transactionDAO, contextHolderFacade);
    }

    @Bean
    public SummaryService simpleSummaryService(NodeDAO nodeDAO, ContextHolderFacade contextHolderFacade,
                                               CurrencyService simpleCurrencyService) {
        return new SummarySimpleService(nodeDAO, contextHolderFacade, simpleCurrencyService);
    }

    @Bean
    public UserService simpleUserService(UserDAO userDAO, ContextHolderFacade contextHolderFacade) {
        return new UserSimpleService(userDAO, contextHolderFacade);
    }

    @Bean
    public FasFacade fasFacade(
            TransactionService simpleTransactionService,
            NodeService simpleNodeService) {
        return new FasSimpleFacade(simpleNodeService, simpleTransactionService);
    }

    @Bean
    public CurrencyService simpleCurrencyService(
            CurrencyDAO currencyCachedDAO) {
        return new CurrencySimpleService(currencyCachedDAO);
    }
}
