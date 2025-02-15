package kraynov.n.financialaccountingsystembackend.utils;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.model.NodeDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeMockDAO implements NodeDAO {

    private final Map<String, NodeDto> nodes = new HashMap<>();

    @Override
    public NodeDto save(NodeDto node) {
        nodes.put(node.getId(), node);
        return node;
    }

    @Override
    public NodeDto getById(String nodeId) {
        return nodes.get(nodeId);
    }

    @Override
    public List<NodeDto> getAll(String userId) {
        return (List<NodeDto>) nodes.values();
    }

    @Override
    public NodeDto update(NodeDto node, String userId) {
        nodes.put(node.getId(), node);
        return node;
    }
}
