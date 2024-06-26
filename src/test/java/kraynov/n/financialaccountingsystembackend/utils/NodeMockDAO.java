package kraynov.n.financialaccountingsystembackend.utils;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.model.Node;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeMockDAO implements NodeDAO {

    private final Map<String, Node> nodes = new HashMap<>();

    @Override
    public Node save(Node node) {
        nodes.put(node.getId(), node);
        return node;
    }

    @Override
    public Node getById(String nodeId) {
        return nodes.get(nodeId);
    }

    @Override
    public List<Node> getAll(String userId) {
        return (List<Node>) nodes.values();
    }

    @Override
    public Node update(Node node, String userId) {
        nodes.put(node.getId(), node);
        return node;
    }
}
