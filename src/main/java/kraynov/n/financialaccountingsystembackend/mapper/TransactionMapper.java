package kraynov.n.financialaccountingsystembackend.mapper;

import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.model.Transaction;
import kraynov.n.financialaccountingsystembackend.service.NodeService;
import kraynov.n.financialaccountingsystembackend.to.TransactionVO;
import kraynov.n.financialaccountingsystembackend.utils.Currency;

public class TransactionMapper {
    private final NodeService nodeService;

    public TransactionMapper(NodeService nodeService) {
        this.nodeService = nodeService;
    }

    public TransactionVO viewObjectFromEntity(Transaction transaction) {
        Node senderNode = nodeService.get(transaction.getSenderNodeId());
        Node receiverNode = nodeService.get(transaction.getReceiverNodeId());
        return TransactionVO.builder()
                .setId(transaction.getId())
                .setDescription(transaction.getDescription())
                .setSenderNodeId(transaction.getSenderNodeId())
                .setReceiverNodeId(transaction.getReceiverNodeId())
                .setSenderNodeName(senderNode.getName())
                .setReceiverNodeName(receiverNode.getName())
                .setSenderAmount(transaction.getSenderAmount())
                .setReceiverAmount(transaction.getReceiverAmount())
                .setSenderCurrencyId(senderNode.getCurrencyId())
                .setReceiverCurrencyId(receiverNode.getCurrencyId())
                .setSenderCurrencySymbol(Currency.define(senderNode.getCurrencyId()).getSymbol())
                .setReceiverCurrencySymbol(Currency.define(receiverNode.getCurrencyId()).getSymbol())
                .setDate(transaction.getDateTime())
                .setCancelled(transaction.isCancelled())
                .setUserId(transaction.getUserId())
                .build();
    }
}
