package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;
import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.model.Transaction;
import kraynov.n.financialaccountingsystembackend.model.UserDTO;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleNodeImpl;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleTransactionImpl;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.NodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
        UserDTO userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();

        validate(node);

        Node nodeWithId = new SimpleNodeImpl.Builder()
                .from(node)
                .setId(UUID.randomUUID().toString())
                .setUserId(userDTO.getId())
                .setOverdraft(node.isExternal() ? Boolean.TRUE : node.isOverdraft())
                .build();
        LOGGER.debug("Start adding node {}", node);
        return nodeDAO.save(nodeWithId);
    }

    private void validate(Node node) {
        if (!node.isOverdraft() && node.isExternal()) {
            LOGGER.warn("External node {} is not overdraft", node);
        }
        if (!node.isOverdraft() && !node.isExternal() && node.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Node should be overdraft to have negative balance"); //todo
        }
    }

    @Override
    public Node edit(Node node) {
        LOGGER.debug("Start editing node with id={}", node.getId());

        validate(node);

        UserDTO userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        return nodeDAO.update(node, userDTO.getId());
    }

    @Override
    public Node get(String id) {
        return nodeDAO.getById(id);
    }

    @Override
    public List<Node> getAll() {
        UserDTO userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        LOGGER.debug("Start loading all nodes for user with id {}", userDTO.getId());
        return nodeDAO.getAll(userDTO.getId());
    }

    @Transactional
    @Override
    public Node archive(String id) {
        UserDTO userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        Node nodeToArchive = nodeDAO.getById(id);
        if (nodeToArchive.isArchived()) {
            throw new IllegalArgumentException("Node " + nodeToArchive.getName() + " is already archived");
        }
        Node arhivedNode = new SimpleNodeImpl.Builder()
                .from(nodeToArchive)
                .setArchived(Boolean.TRUE)
                .build();
        return nodeDAO.update(arhivedNode, userDTO.getId());
    }

    @Transactional
    @Override
    public Node restore(String id) {
        UserDTO userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        Node nodeToRestore = nodeDAO.getById(id);
        if (!nodeToRestore.isArchived()) {
            throw new IllegalArgumentException("Node " + nodeToRestore.getName() + " is not archived");
        }
        Node restoredNode = new SimpleNodeImpl.Builder()
                .from(nodeToRestore)
                .setArchived(Boolean.FALSE)
                .build();
        return nodeDAO.update(restoredNode, userDTO.getId());
    }

    @Override
    public void calculateTransactionAffection(Transaction transaction) throws InsufficientFundsException {
        LOGGER.debug("Start calculating transaction {}", transaction);
        try {
            calculate(transaction);
        } catch (InsufficientFundsException e) {
            LOGGER.debug("Not enough amount ({}) on sender node with id = {} ({})", transaction.getSenderAmount(),
                    transaction.getSenderNodeId(), transaction.getSenderAmount());
            throw e;
        }
    }

    @Override
    public void cancelTransactionAffection(Transaction transaction) throws InsufficientFundsException {
        Transaction reversedTransaction = SimpleTransactionImpl.builder()
                .from(transaction)
                .setSenderAmount(transaction.getSenderAmount().negate())
                .setReceiverAmount(transaction.getReceiverAmount().negate())
                .build();
        try {
            calculate(reversedTransaction);
        } catch (InsufficientFundsException e) {
            LOGGER.debug("Not enough amount ({}) on receiver node with id = {} ({}) to cancel transaction",
                    transaction.getSenderAmount(), transaction.getSenderNodeId(), transaction.getSenderAmount());
            throw e;
        }
    }

    private void calculate(Transaction transaction) throws InsufficientFundsException {
        Node senderNode = nodeDAO.getById(transaction.getSenderNodeId());
        Node receiverNode = nodeDAO.getById(transaction.getReceiverNodeId());

        UserDTO userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        if (!senderNode.getUserId().equals(userDTO.getId()) ||
                !receiverNode.getUserId().equals(userDTO.getId())) {
            LOGGER.warn("Requested transaction from another user node");
            throw new IllegalArgumentException("Requested transaction from another user node");
        }

        Node newSenderNode = new SimpleNodeImpl.Builder()
                .from(senderNode)
                .setAmount(senderNode.getAmount().subtract(transaction.getSenderAmount()))
                .build();

        Node newReceiverNode = new SimpleNodeImpl.Builder()
                .from(receiverNode)
                .setAmount(receiverNode.getAmount().add(transaction.getReceiverAmount()))
                .build();

        if (!newSenderNode.isOverdraft() && !newSenderNode.isExternal() && BigDecimal.ZERO.compareTo(newSenderNode.getAmount()) > 0) {
            throw new InsufficientFundsException("Not enough amount on sender node");
        }

        nodeDAO.update(newSenderNode, userDTO.getId());
        nodeDAO.update(newReceiverNode, userDTO.getId());
    }
}
