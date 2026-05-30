package kraynov.n.financialaccountingsystembackend.service.config;

import kraynov.n.financialaccountingsystembackend.client.RateClient;
import kraynov.n.financialaccountingsystembackend.dao.CurrencyDAO;
import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.dao.TransactionDAO;
import kraynov.n.financialaccountingsystembackend.dao.TransactionExtendedInfoDAO;
import kraynov.n.financialaccountingsystembackend.dao.UserDAO;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;
import kraynov.n.financialaccountingsystembackend.service.FasFacade;
import kraynov.n.financialaccountingsystembackend.service.NodeService;
import kraynov.n.financialaccountingsystembackend.service.RateService;
import kraynov.n.financialaccountingsystembackend.service.SummaryService;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;
import kraynov.n.financialaccountingsystembackend.service.UserService;
import kraynov.n.financialaccountingsystembackend.service.impl.CurrencySimpleService;
import kraynov.n.financialaccountingsystembackend.service.impl.FasSimpleFacade;
import kraynov.n.financialaccountingsystembackend.service.impl.NodeSimpleService;
import kraynov.n.financialaccountingsystembackend.service.impl.RateServiceImpl;
import kraynov.n.financialaccountingsystembackend.service.impl.SummarySimpleService;
import kraynov.n.financialaccountingsystembackend.service.impl.TransactionSimpleService;
import kraynov.n.financialaccountingsystembackend.service.impl.UserSimpleService;
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
                                               TransactionService transactionService,
                                               CurrencyService simpleCurrencyService) {
        return new SummarySimpleService(nodeDAO, transactionService, contextHolderFacade, simpleCurrencyService);
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

    @Bean
    public RateService rateService(
            RateClient rateClient,
            NodeService nodeService,
            ContextHolderFacade contextHolderFacade) {
        return new RateServiceImpl(rateClient, nodeService, contextHolderFacade);
    }
}