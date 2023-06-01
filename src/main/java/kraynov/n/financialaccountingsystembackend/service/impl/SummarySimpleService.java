package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.model.UserDTO;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.SummaryService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SummarySimpleService implements SummaryService {
    private final NodeDAO nodeDAO;

    public final ContextHolderFacade contextHolderFacade;

    public SummarySimpleService(NodeDAO nodeDAO, ContextHolderFacade contextHolderFacade) {
        this.nodeDAO = nodeDAO;
        this.contextHolderFacade = contextHolderFacade;
    }

    @Override
    public Map<Integer, BigDecimal> getSum() {
        UserDTO userDTO = contextHolderFacade.getAuthenticatedUser();
        if (userDTO == null) {
            throw new IllegalStateException();
        }
        List<Node> nodes = nodeDAO.getAll(userDTO.getId()).stream().filter(n -> !n.isExternal()).toList();
        Map<Integer, BigDecimal> sum = new HashMap<>();
        for (Node node : nodes) {
            sum.merge(node.getCurrencyId(), node.getAmount(), BigDecimal::add);
        }
        return sum;
    }
}
