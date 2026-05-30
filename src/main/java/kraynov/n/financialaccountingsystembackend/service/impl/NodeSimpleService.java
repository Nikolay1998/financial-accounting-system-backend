package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.NodeDao;
import kraynov.n.financialaccountingsystembackend.dto.NodeDto;
import kraynov.n.financialaccountingsystembackend.dto.NodeExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.dto.TransactionDto;
import kraynov.n.financialaccountingsystembackend.dto.UserDetailsDto;
import kraynov.n.financialaccountingsystembackend.exception.ForbiddenOperationException;
import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;
import kraynov.n.financialaccountingsystembackend.exception.InvalidOperationException;
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
    public final NodeDao nodeDao;
    public final ContextHolderFacade contextHolderFacade;

    public NodeSimpleService(
            NodeDao nodeDao,
            ContextHolderFacade contextHolderFacade
    ) {
        this.nodeDao = nodeDao;
        this.contextHolderFacade = contextHolderFacade;
    }

    @Override
    public NodeExtendedInfoDto add(NodeDto node) {
        UserDetailsDto userDto = contextHolderFacade.getAuthenticatedUserOrThrowException();

        validate(node);

        NodeDto nodeWithId = node.toBuilder()
                .id(UUID.randomUUID().toString())
                .userId(userDto.getId())
                .isOverdraft(node.isExternal() ? Boolean.TRUE : node.isOverdraft())
                .build();
        LOGGER.debug("Start adding node {}", node);
        nodeDao.save(nodeWithId);

        return nodeDao.getExtendedInfoById(nodeWithId.getId());
    }

    private void validate(NodeDto node) {
        if (!node.isOverdraft() && node.isExternal()) {
            LOGGER.warn("External node {} is not overdraft", node);
        }
        if (!node.isOverdraft() && !node.isExternal() && node.getAmount().compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidOperationException(
                    String.format("Node %s is not overdraft and have negative balance", node.getId()),
                    "node should be overdraft to have negative balance");
        }
    }

    @Override
    public NodeExtendedInfoDto edit(NodeDto node) {
        LOGGER.debug("Start editing node with id={}", node.getId());

        validate(node);

        UserDetailsDto userDto = contextHolderFacade.getAuthenticatedUserOrThrowException();
        nodeDao.update(node, userDto.getId());

        return nodeDao.getExtendedInfoById(node.getId());
    }

    @Override
    public NodeExtendedInfoDto get(String id) {
        UserDetailsDto userDto = contextHolderFacade.getAuthenticatedUserOrThrowException();
        NodeDto node = nodeDao.getById(id);
        if (node == null) {
            throw new InvalidOperationException(
                    String.format("Node %s not found", id),
                    "node not found");
        }
        if (!node.getUserId().equals(userDto.getId())) {
            throw new ForbiddenOperationException("Requested node belongs to another user");
        }
        return nodeDao.getExtendedInfoById(id);
    }

    @Override
    public List<NodeExtendedInfoDto> getAllByUser(String userId) {
        LOGGER.debug("Start loading all nodes for user with id {}", userId);
        return nodeDao.getAll(userId);
    }

    @Transactional
    @Override
    public NodeExtendedInfoDto archive(String id) {
        UserDetailsDto userDto = contextHolderFacade.getAuthenticatedUserOrThrowException();
        NodeDto nodeToArchive = nodeDao.getById(id);
        if (nodeToArchive.isArchived()) {
            throw new InvalidOperationException(
                    String.format("Node %s is already archived", nodeToArchive.getId()),
                    String.format("node %s is already archived", nodeToArchive.getName()));
        }

        NodeDto archivedNode = nodeToArchive
                .withArchived(Boolean.TRUE);

        nodeDao.update(archivedNode, userDto.getId());
        return nodeDao.getExtendedInfoById(id);
    }

    @Transactional
    @Override
    public NodeExtendedInfoDto restore(String id) {
        UserDetailsDto userDto = contextHolderFacade.getAuthenticatedUserOrThrowException();
        NodeDto nodeToRestore = nodeDao.getById(id);
        if (!nodeToRestore.isArchived()) {
            throw new InvalidOperationException(
                    String.format("Node %s is not archived", nodeToRestore.getId()),
                    String.format("node %s is not archived", nodeToRestore.getName()));
        }

        NodeDto restoredNode = nodeToRestore
                .withArchived(Boolean.FALSE);

        nodeDao.update(restoredNode, userDto.getId());
        return nodeDao.getExtendedInfoById(id);
    }

    @Override
    public void calculateTransactionAffection(TransactionDto transaction) {
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
    public void cancelTransactionAffection(TransactionDto transaction) {
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

    private void calculate(TransactionDto transaction) {
        NodeDto senderNode = nodeDao.getById(transaction.getSenderNodeId());
        NodeDto receiverNode = nodeDao.getById(transaction.getReceiverNodeId());

        UserDetailsDto userDto = contextHolderFacade.getAuthenticatedUserOrThrowException();
        if (!senderNode.getUserId().equals(userDto.getId()) ||
                !receiverNode.getUserId().equals(userDto.getId())) {
            throw new ForbiddenOperationException("Requested transaction from another user node");
        }

        NodeDto newSenderNode = senderNode
                .withAmount(senderNode.getAmount().subtract(transaction.getSenderAmount()));

        NodeDto newReceiverNode = receiverNode
                .withAmount(receiverNode.getAmount().add(transaction.getReceiverAmount()));

        if (!newSenderNode.isOverdraft() && !newSenderNode.isExternal() && BigDecimal.ZERO.compareTo(newSenderNode.getAmount()) > 0) {
            throw new InsufficientFundsException("Not enough amount on sender node");
        }

        nodeDao.update(newSenderNode, userDto.getId());
        nodeDao.update(newReceiverNode, userDto.getId());
    }
}
