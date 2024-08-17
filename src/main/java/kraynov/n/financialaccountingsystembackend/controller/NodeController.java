package kraynov.n.financialaccountingsystembackend.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kraynov.n.financialaccountingsystembackend.mapper.NodeMapper;
import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.service.NodeService;
import kraynov.n.financialaccountingsystembackend.to.NodeVO;

@RestController
@RequestMapping(path = "/node")
public class NodeController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final NodeService nodeService;

    private final NodeMapper nodeMapper;

    public NodeController(NodeService nodeService, NodeMapper nodeMapper) {
        this.nodeService = nodeService;
        this.nodeMapper = nodeMapper;
    }

    @CrossOrigin
    @GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NodeVO> getAll() {
        List<NodeVO> nodes = nodeService.getAll()
                .stream()
                .map(nodeMapper::viewObjectFromEntity)
                .sorted(NodeMapper::compareNodeVO)
                .toList();
        logger.debug("find {} nodes by user", nodes.size());
        return nodes;
    }

    @CrossOrigin
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Node add(@RequestBody NodeVO node) {
        return nodeService.add(nodeMapper.entityFromViewObject(node));
    }

    @CrossOrigin
    @PutMapping(path = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public NodeVO editNode(@RequestBody NodeVO nodeVo) {
        Node node = nodeMapper.entityFromViewObject(nodeVo);
        logger.debug("Converted node for editing: {}", node);
        Node edited = nodeService.edit(node);
        if (edited == null) {
            throw new IllegalStateException("Can't find node for edit with id=" + node.getId());
        }
        return nodeMapper.viewObjectFromEntity(edited);
    }

}
