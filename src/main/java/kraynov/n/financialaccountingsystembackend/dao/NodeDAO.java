package kraynov.n.financialaccountingsystembackend.dao;

import kraynov.n.financialaccountingsystembackend.model.NodeDto;

import java.util.List;

public interface NodeDAO {
    NodeDto save(NodeDto node);

    NodeDto getById(String nodeId);

    List<NodeDto> getAll(String userId);

    NodeDto update(NodeDto node, String userId);
}
