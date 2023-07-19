package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.model.UserDTO;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleNodeImpl;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

public class NodeSimpleService implements NodeService {

    private final static Logger LOGGER = LoggerFactory.getLogger(NodeSimpleService.class);
    public final NodeDAO nodeDAO;
    public final ContextHolderFacade contextHolderFacade;

    public NodeSimpleService(NodeDAO nodeDAO, ContextHolderFacade contextHolderFacade) {
        this.nodeDAO = nodeDAO;
        this.contextHolderFacade = contextHolderFacade;
    }

    @Override
    public Node add(Node node) {
        UserDTO userDTO = contextHolderFacade.getAuthenticatedUser();
        if (userDTO == null) {
            LOGGER.error("Can't find authenticated user while adding node {}", node);
            //toDO: think about it
            throw new IllegalStateException();
        }
        Node nodeWithId = new SimpleNodeImpl.Builder()
                .from(node)
                .setId(UUID.randomUUID().toString())
                .setUserId(userDTO.getId())
                .build();
        LOGGER.debug("Start adding node {}", node);
        return nodeDAO.save(nodeWithId);
    }

    @Override
    public List<Node> getAll() {
        UserDTO userDTO = contextHolderFacade.getAuthenticatedUser();
        if (userDTO == null) {
            LOGGER.error("Can't find authenticated user while loading all nodes");
            //toDO: think about it
            throw new IllegalStateException();
        }
        //toDo: use userDTO
        LOGGER.debug("Start loading all nodes");
        return nodeDAO.getAll(userDTO.getId());
    }
}
