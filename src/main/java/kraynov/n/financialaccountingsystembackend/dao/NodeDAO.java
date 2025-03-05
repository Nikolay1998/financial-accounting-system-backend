package kraynov.n.financialaccountingsystembackend.dao;

import kraynov.n.financialaccountingsystembackend.dto.NodeDto;
import kraynov.n.financialaccountingsystembackend.dto.NodeExtendedInfoDto;

import java.util.List;

public interface NodeDAO {
    NodeDto save(NodeDto node);

    NodeExtendedInfoDto getExtendedInfoById(String nodeId);

    NodeDto getById(String nodeId);

    List<NodeExtendedInfoDto> getAll(String userId);

    NodeDto update(NodeDto node, String userId);
}
