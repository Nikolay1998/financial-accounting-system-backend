package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.model.Node;

import java.util.List;

public interface NodeService {
    public Node add(Node node);

    public List<Node> getAll();
}
