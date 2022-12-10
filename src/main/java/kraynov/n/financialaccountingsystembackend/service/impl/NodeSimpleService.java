package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.service.NodeService;

import java.util.List;

public class NodeSimpleService implements NodeService {
    public final NodeDAO nodeDAO;

    public NodeSimpleService(NodeDAO nodeDAO) {
        this.nodeDAO = nodeDAO;
    }

    @Override
    public Node add(Node node) {
        return nodeDAO.save(node);
    }

    @Override
    public List<Node> getAll() {
        return nodeDAO.getAll();
    }
}
