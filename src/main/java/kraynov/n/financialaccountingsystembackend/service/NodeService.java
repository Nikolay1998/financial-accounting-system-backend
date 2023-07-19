package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.model.Node;

import java.util.List;

public interface NodeService {
    Node add(Node node);

    List<Node> getAll();
}
