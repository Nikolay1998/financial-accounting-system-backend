package kraynov.n.financialaccountingsystembackend.dao;

import kraynov.n.financialaccountingsystembackend.model.Node;

import java.util.List;

public interface NodeDAO {
    Node save(Node node);

    Node getById(int nodeId);

    List<Node> getAll();

    Node update(Node node);
}
