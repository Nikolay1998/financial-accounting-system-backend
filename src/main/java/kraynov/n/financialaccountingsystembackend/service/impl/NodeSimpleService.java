package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleNodeImpl;
import kraynov.n.financialaccountingsystembackend.service.NodeService;

import java.util.List;
import java.util.UUID;

public class NodeSimpleService implements NodeService {
    public final NodeDAO nodeDAO;

    public NodeSimpleService(NodeDAO nodeDAO) {
        this.nodeDAO = nodeDAO;
    }

    @Override
    public Node add(Node node) {
        Node nodeWithId = new SimpleNodeImpl.Builder()
                .from(node)
                .setId(UUID.randomUUID().toString())
                .build();
        return nodeDAO.save(nodeWithId);
    }

    @Override
    public List<Node> getAll() {
        return nodeDAO.getAll();
    }
}
