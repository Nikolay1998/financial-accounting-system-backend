package kraynov.n.financialaccountingsystembackend.mapper;

import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.model.Transaction;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleTransactionImpl;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;
import kraynov.n.financialaccountingsystembackend.service.NodeService;
import kraynov.n.financialaccountingsystembackend.to.TransactionVO;

public class TransactionMapper {
    private final NodeService nodeService;
    private final CurrencyService currencyService;

    public TransactionMapper(NodeService nodeService, CurrencyService currencyService) {
        this.nodeService = nodeService;
        this.currencyService = currencyService;
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
                .setSenderCurrencySymbol(currencyService.getById(senderNode.getCurrencyId()).getSymbol())
                .setReceiverCurrencySymbol(currencyService.getById(receiverNode.getCurrencyId()).getSymbol())
                .setDate(transaction.getDateTime())
                .setCancelled(transaction.isCancelled())
                .setUserId(transaction.getUserId())
                .build();
    }

    public Transaction entityFromViewObject(TransactionVO transactionVO) {
        return SimpleTransactionImpl.builder()
                .setId(null)
                .setDescription(transactionVO.getDescription())
                .setSenderNodeId(transactionVO.getSenderNodeId())
                .setReceiverNodeId(transactionVO.getReceiverNodeId())
                .setSenderAmount(transactionVO.getSenderAmount())
                .setReceiverAmount(transactionVO.getReceiverAmount())
                .setTime(transactionVO.getDate())
                .setCancelled(false)
                .setUserId(null)
                .build();
    }
}
