package kraynov.n.financialaccountingsystembackend.service.config;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.service.NodeService;
import kraynov.n.financialaccountingsystembackend.service.impl.NodeSimpleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NodeServiceConfiguration {
    @Bean
    public NodeService simpleNodeService(NodeDAO nodeDAO) {
        return new NodeSimpleService(nodeDAO);
    }
}
