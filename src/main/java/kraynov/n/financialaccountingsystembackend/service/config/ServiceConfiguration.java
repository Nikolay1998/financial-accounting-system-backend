package kraynov.n.financialaccountingsystembackend.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import kraynov.n.financialaccountingsystembackend.dao.CurrencyDAO;
import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.dao.TransactionDAO;
import kraynov.n.financialaccountingsystembackend.dao.UserDAO;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;
import kraynov.n.financialaccountingsystembackend.service.FASFacade;
import kraynov.n.financialaccountingsystembackend.service.NodeService;
import kraynov.n.financialaccountingsystembackend.service.SummaryService;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;
import kraynov.n.financialaccountingsystembackend.service.UserService;
import kraynov.n.financialaccountingsystembackend.service.impl.CurrencySimpleService;
import kraynov.n.financialaccountingsystembackend.service.impl.FASSimpleFacade;
import kraynov.n.financialaccountingsystembackend.service.impl.NodeSimpleService;
import kraynov.n.financialaccountingsystembackend.service.impl.SummarySimpleService;
import kraynov.n.financialaccountingsystembackend.service.impl.TransactionSimpleService;
import kraynov.n.financialaccountingsystembackend.service.impl.UserSimpleService;

@Configuration
public class ServiceConfiguration {
    @Bean
    public NodeService simpleNodeService(NodeDAO nodeDAO, ContextHolderFacade contextHolderFacade) {
        return new NodeSimpleService(nodeDAO, contextHolderFacade);
    }

    @Bean
    public TransactionService simpleTransactionService(TransactionDAO transactionDAO,
            ContextHolderFacade contextHolderFacade) {
        return new TransactionSimpleService(transactionDAO, contextHolderFacade);
    }

    @Bean
    public SummaryService simpleSummaryService(NodeDAO nodeDAO, ContextHolderFacade contextHolderFacade) {
        return new SummarySimpleService(nodeDAO, contextHolderFacade);
    }

    @Bean
    public UserService simpleUserService(UserDAO userDAO, ContextHolderFacade contextHolderFacade) {
        return new UserSimpleService(userDAO, contextHolderFacade);
    }

    @Bean
    public FASFacade fasFacade(
            TransactionService simpleTransactionService,
            NodeService simpleNodeService) {
        return new FASSimpleFacade(simpleNodeService, simpleTransactionService);
    }

    @Bean
    public CurrencyService simpleCurrencyService(
            CurrencyDAO currencyDAO) {
        return new CurrencySimpleService(currencyDAO);
    }
}
