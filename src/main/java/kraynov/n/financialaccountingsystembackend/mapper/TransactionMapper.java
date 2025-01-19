package kraynov.n.financialaccountingsystembackend.mapper;

import kraynov.n.financialaccountingsystembackend.model.Transaction;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleTransactionImpl;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;
import kraynov.n.financialaccountingsystembackend.service.NodeService;
import kraynov.n.financialaccountingsystembackend.to.TransactionVO;

public class TransactionMapper {
    private final CurrencyService currencyService;

    public TransactionMapper(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public TransactionVO viewObjectFromEntity(Transaction transaction) {
        return TransactionVO.builder()
                .setId(transaction.getId())
                .setDescription(transaction.getDescription())
                .setSenderNodeId(transaction.getSenderNodeId())
                .setReceiverNodeId(transaction.getReceiverNodeId())
                .setSenderNodeName(transaction.getSenderName())
                .setReceiverNodeName(transaction.getReceiverName())
                .setSenderAmount(transaction.getSenderAmount())
                .setReceiverAmount(transaction.getReceiverAmount())
                .setSenderCurrencyId(transaction.getSenderCurrencyId())
                .setReceiverCurrencyId(transaction.getReceiverCurrencyId())
                .setSenderCurrencySymbol(currencyService.getById(transaction.getSenderCurrencyId()).getSymbol())
                .setReceiverCurrencySymbol(currencyService.getById(transaction.getReceiverCurrencyId()).getSymbol())
                .setDate(transaction.getDateTime())
                .setCancelled(transaction.isCancelled())
                .setUserId(transaction.getUserId())
                .setFromExternal(transaction.isFromExternal())
                .setToExternal(transaction.isToExternal())
                .build();
    }

    public Transaction entityFromViewObject(TransactionVO transactionVO) {
        return SimpleTransactionImpl.builder()
                .setId(transactionVO.getId())
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
