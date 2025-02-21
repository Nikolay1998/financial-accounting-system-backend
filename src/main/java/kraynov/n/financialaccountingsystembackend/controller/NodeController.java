package kraynov.n.financialaccountingsystembackend.controller;

import kraynov.n.financialaccountingsystembackend.dto.NodeDto;
import kraynov.n.financialaccountingsystembackend.dto.NodeExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.mapper.NodeMapper;
import kraynov.n.financialaccountingsystembackend.service.NodeService;
import kraynov.n.financialaccountingsystembackend.to.NodeRequestTO;
import kraynov.n.financialaccountingsystembackend.to.NodeResponseTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<NodeResponseTO> getAll() {
        List<NodeResponseTO> nodes = nodeService.getAll()
                .stream()
                .map(nodeMapper::responseFromDto)
                .sorted(NodeMapper::compareNodeVO)
                .toList();
        logger.debug("find {} nodes by user", nodes.size());
        return nodes;
    }

    @CrossOrigin
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public NodeResponseTO add(@RequestBody NodeRequestTO node) {
        return nodeMapper.responseFromDto(
                nodeService.add(nodeMapper.dtoFromRequest(node)));
    }

    @CrossOrigin
    @PutMapping(path = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public NodeResponseTO editNode(@RequestBody NodeRequestTO nodeRequestTO) {
        NodeDto node = nodeMapper.dtoFromRequest(nodeRequestTO);
        logger.debug("Converted node for editing: {}", node);
        NodeExtendedInfoDto edited = nodeService.edit(node);
        if (edited == null) {
            throw new IllegalStateException("Can't find node for edit with id=" + node.getId());
        }
        return nodeMapper.responseFromDto(edited);
    }

    @CrossOrigin
    @PutMapping(path = "/archive", consumes = MediaType.APPLICATION_JSON_VALUE)
    public NodeResponseTO archiveNode(@RequestParam String nodeId) {
        NodeExtendedInfoDto archived = nodeService.archive(nodeId);
        return nodeMapper.responseFromDto(archived);
    }

    @CrossOrigin
    @PutMapping(path = "/restore", consumes = MediaType.APPLICATION_JSON_VALUE)
    public NodeResponseTO restoreNode(@RequestParam String nodeId) {
        NodeExtendedInfoDto restored = nodeService.restore(nodeId);
        return nodeMapper.responseFromDto(restored);
    }

}
