package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.service.SummaryService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SummarySimpleService implements SummaryService {
    private final NodeDAO nodeDAO;

    public SummarySimpleService(NodeDAO nodeDAO) {
        this.nodeDAO = nodeDAO;
    }

    @Override
    public Map<Integer, BigDecimal> getSum() {
        List<Node> nodes = nodeDAO.getAll().stream().filter(n -> !n.isExternal()).toList();
        Map<Integer, BigDecimal> sum = new HashMap<>();
        for (Node node : nodes) {
            sum.merge(node.getCurrencyId(), node.getAmount(), BigDecimal::add);
        }
        return sum;
    }
}
