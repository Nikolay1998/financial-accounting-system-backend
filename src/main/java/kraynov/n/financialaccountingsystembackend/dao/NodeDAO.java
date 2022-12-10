package kraynov.n.financialaccountingsystembackend.dao;

import kraynov.n.financialaccountingsystembackend.model.Node;

import java.util.List;

public interface NodeDAO {
    Node save(Node node);
    List<Node> getAll();
}
