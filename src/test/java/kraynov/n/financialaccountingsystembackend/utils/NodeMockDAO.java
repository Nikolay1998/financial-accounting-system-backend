package kraynov.n.financialaccountingsystembackend.utils;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.model.NodeDto;
import kraynov.n.financialaccountingsystembackend.model.NodeExtendedInfoDto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NodeMockDAO implements NodeDAO {

    private final Map<String, NodeDto> nodes = new HashMap<>();

    @Override
    public NodeDto save(NodeDto node) {
        nodes.put(node.getId(), node);
        return node;
    }

    @Override
    public NodeExtendedInfoDto getExtendedInfoById(String nodeId) {
        return null;
    }

    @Override
    public NodeDto getById(String nodeId) {
        return nodes.get(nodeId);
    }

    @Override
    public List<NodeExtendedInfoDto> getAll(String userId) {
        return nodes.values().stream().map(nodeDto -> NodeExtendedInfoDto
                        .builder()
                        .id(nodeDto.getId())
                        .name(nodeDto.getName())
                        .lastTransactionDate(LocalDate.of(2020, 1, 1))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public NodeDto update(NodeDto node, String userId) {
        nodes.put(node.getId(), node);
        return node;
    }
}
