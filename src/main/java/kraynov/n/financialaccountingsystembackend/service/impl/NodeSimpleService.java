package kraynov.n.financialaccountingsystembackend.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;
import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.model.Transaction;
import kraynov.n.financialaccountingsystembackend.model.UserDTO;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleNodeImpl;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleTransactionImpl;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.NodeService;

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

        Node nodeWithId = new SimpleNodeImpl.Builder()
                .from(node)
                .setId(UUID.randomUUID().toString())
                .setUserId(userDTO.getId())
                .build();
        LOGGER.debug("Start adding node {}", node);
        return nodeDAO.save(nodeWithId);
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
        String senderNodeId = transaction.getSenderNodeId();
        String receiverNodeId = transaction.getReceiverNodeId();
        Transaction reversedTransaction = SimpleTransactionImpl.builder()
                .from(transaction)
                .setSenderNodeId(receiverNodeId)
                .setReceiverNodeId(senderNodeId)
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

        if (!newSenderNode.isExternal() && BigDecimal.ZERO.compareTo(newSenderNode.getAmount()) > 0) {
            throw new InsufficientFundsException("Not enough amount on sender node");
        }

        nodeDAO.update(newSenderNode);
        nodeDAO.update(newReceiverNode);
    }
}
