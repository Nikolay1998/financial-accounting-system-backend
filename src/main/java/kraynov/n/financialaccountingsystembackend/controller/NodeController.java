package kraynov.n.financialaccountingsystembackend.controller;

import kraynov.n.financialaccountingsystembackend.dto.NodeDto;
import kraynov.n.financialaccountingsystembackend.dto.NodeExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.dto.UserDetailsDto;
import kraynov.n.financialaccountingsystembackend.exception.InvalidOperationException;
import kraynov.n.financialaccountingsystembackend.mapper.NodeMapper;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.NodeService;
import kraynov.n.financialaccountingsystembackend.to.NodeRequestTo;
import kraynov.n.financialaccountingsystembackend.to.NodeResponseTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/node")
public class NodeController {

    public final ContextHolderFacade contextHolderFacade;
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final NodeService nodeService;
    private final NodeMapper nodeMapper;

    public NodeController(
            NodeService nodeService,
            NodeMapper nodeMapper,
            ContextHolderFacade contextHolderFacade
    ) {
        this.nodeService = nodeService;
        this.nodeMapper = nodeMapper;
        this.contextHolderFacade = contextHolderFacade;
    }

    @CrossOrigin
    @GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NodeResponseTo> getAll() {
        UserDetailsDto userDto = contextHolderFacade.getAuthenticatedUserOrThrowException();
        List<NodeResponseTo> nodes = nodeService.getAllByUser(userDto.getId())
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
    public NodeResponseTo add(@RequestBody NodeRequestTo node) {
        return nodeMapper.responseFromDto(
                nodeService.add(nodeMapper.dtoFromRequest(node)));
    }

    @CrossOrigin
    @PutMapping(path = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public NodeResponseTo editNode(@RequestBody NodeRequestTo nodeRequestTo) {
        NodeDto node = nodeMapper.dtoFromRequest(nodeRequestTo);
        logger.debug("Converted node for editing: {}", node);
        NodeExtendedInfoDto edited = nodeService.edit(node);
        if (edited == null) {
            throw new InvalidOperationException(
                    String.format("Node with id '%s' not found", node.id()),
                    String.format("node with name '%s' not found", node.name())
            );
        }
        return nodeMapper.responseFromDto(edited);
    }

    @CrossOrigin
    @PutMapping(path = "/archive", consumes = MediaType.APPLICATION_JSON_VALUE)
    public NodeResponseTo archiveNode(@RequestParam String nodeId) {
        NodeExtendedInfoDto archived = nodeService.archive(nodeId);
        return nodeMapper.responseFromDto(archived);
    }

    @CrossOrigin
    @PutMapping(path = "/restore", consumes = MediaType.APPLICATION_JSON_VALUE)
    public NodeResponseTo restoreNode(@RequestParam String nodeId) {
        NodeExtendedInfoDto restored = nodeService.restore(nodeId);
        return nodeMapper.responseFromDto(restored);
    }

}
