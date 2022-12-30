package kraynov.n.financialaccountingsystembackend.service.config;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.dao.TransactionDAO;
import kraynov.n.financialaccountingsystembackend.service.NodeService;
import kraynov.n.financialaccountingsystembackend.service.SummaryService;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;
import kraynov.n.financialaccountingsystembackend.service.impl.NodeSimpleService;
import kraynov.n.financialaccountingsystembackend.service.impl.SummarySimpleService;
import kraynov.n.financialaccountingsystembackend.service.impl.TransactionSimpleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {
    @Bean
    public NodeService simpleNodeService(NodeDAO nodeDAO) {
        return new NodeSimpleService(nodeDAO);
    }

    @Bean
    public TransactionService simpleTransactionService(TransactionDAO transactionDAO, NodeDAO nodeDAO) {
        return new TransactionSimpleService(transactionDAO, nodeDAO);
    }

    @Bean
    public SummaryService simpleSummaryService(NodeDAO nodeDAO) {
        return new SummarySimpleService(nodeDAO);
    }
}
