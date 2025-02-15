package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;
import kraynov.n.financialaccountingsystembackend.model.NodeDto;
import kraynov.n.financialaccountingsystembackend.model.TransactionDto;
import kraynov.n.financialaccountingsystembackend.model.UserDetailsDto;
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
    public NodeDto add(NodeDto node) {
        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();

        validate(node);

        NodeDto nodeWithId = node.toBuilder()
                .id(UUID.randomUUID().toString())
                .userId(userDTO.getId())
                .isOverdraft(node.isExternal() ? Boolean.TRUE : node.isOverdraft())
                .build();
        LOGGER.debug("Start adding node {}", node);
        return nodeDAO.save(nodeWithId);
    }

    private void validate(NodeDto node) {
        if (!node.isOverdraft() && node.isExternal()) {
            LOGGER.warn("External node {} is not overdraft", node);
        }
        if (!node.isOverdraft() && !node.isExternal() && node.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Node should be overdraft to have negative balance"); //todo
        }
    }

    @Override
    public NodeDto edit(NodeDto node) {
        LOGGER.debug("Start editing node with id={}", node.getId());

        validate(node);

        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        return nodeDAO.update(node, userDTO.getId());
    }

    @Override
    public NodeDto get(String id) {
        return nodeDAO.getById(id);
    }

    @Override
    public List<NodeDto> getAll() {
        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        LOGGER.debug("Start loading all nodes for user with id {}", userDTO.getId());
        return nodeDAO.getAll(userDTO.getId());
    }

    @Transactional
    @Override
    public NodeDto archive(String id) {
        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        NodeDto nodeToArchive = nodeDAO.getById(id);
        if (nodeToArchive.isArchived()) {
            throw new IllegalArgumentException("Node " + nodeToArchive.getName() + " is already archived");
        }

        NodeDto archivedNode = nodeToArchive
                .withArchived(Boolean.TRUE);

        return nodeDAO.update(archivedNode, userDTO.getId());
    }

    @Transactional
    @Override
    public NodeDto restore(String id) {
        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        NodeDto nodeToRestore = nodeDAO.getById(id);
        if (!nodeToRestore.isArchived()) {
            throw new IllegalArgumentException("Node " + nodeToRestore.getName() + " is not archived");
        }

        NodeDto restoredNode = nodeToRestore
                .withArchived(Boolean.FALSE);

        return nodeDAO.update(restoredNode, userDTO.getId());
    }

    @Override
    public void calculateTransactionAffection(TransactionDto transaction) throws InsufficientFundsException {
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
    public void cancelTransactionAffection(TransactionDto transaction) throws InsufficientFundsException {
        TransactionDto reversedTransaction = transaction
                .toBuilder()
                .senderAmount(transaction.getSenderAmount().negate())
                .receiverAmount(transaction.getReceiverAmount().negate())
                .build();
        try {
            calculate(reversedTransaction);
        } catch (InsufficientFundsException e) {
            LOGGER.debug("Not enough amount ({}) on receiver node with id = {} ({}) to cancel transaction",
                    transaction.getSenderAmount(), transaction.getSenderNodeId(), transaction.getSenderAmount());
            throw e;
        }
    }

    private void calculate(TransactionDto transaction) throws InsufficientFundsException {
        NodeDto senderNode = nodeDAO.getById(transaction.getSenderNodeId());
        NodeDto receiverNode = nodeDAO.getById(transaction.getReceiverNodeId());

        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
        if (!senderNode.getUserId().equals(userDTO.getId()) ||
                !receiverNode.getUserId().equals(userDTO.getId())) {
            LOGGER.warn("Requested transaction from another user node");
            throw new IllegalArgumentException("Requested transaction from another user node");
        }

        NodeDto newSenderNode = senderNode
                .withAmount(senderNode.getAmount().subtract(transaction.getSenderAmount()));

        NodeDto newReceiverNode = receiverNode
                .withAmount(receiverNode.getAmount().add(transaction.getReceiverAmount()));

        if (!newSenderNode.isOverdraft() && !newSenderNode.isExternal() && BigDecimal.ZERO.compareTo(newSenderNode.getAmount()) > 0) {
            throw new InsufficientFundsException("Not enough amount on sender node");
        }

        nodeDAO.update(newSenderNode, userDTO.getId());
        nodeDAO.update(newReceiverNode, userDTO.getId());
    }
}
