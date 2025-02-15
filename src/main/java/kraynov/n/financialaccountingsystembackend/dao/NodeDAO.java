package kraynov.n.financialaccountingsystembackend.dao;

import kraynov.n.financialaccountingsystembackend.model.NodeDto;
import kraynov.n.financialaccountingsystembackend.model.NodeExtendedInfoDto;

import java.util.List;

public interface NodeDAO {
    NodeDto save(NodeDto node);

    NodeExtendedInfoDto getExtendedInfoById(String nodeId);

    NodeDto getById(String nodeId);

    List<NodeExtendedInfoDto> getAll(String userId);

    NodeDto update(NodeDto node, String userId);
}
