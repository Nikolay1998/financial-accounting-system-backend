package kraynov.n.financialaccountingsystembackend.mapper;

import kraynov.n.financialaccountingsystembackend.model.TransactionDto;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;
import kraynov.n.financialaccountingsystembackend.to.TransactionVO;

public class TransactionMapper {
    private final CurrencyService currencyService;

    public TransactionMapper(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public TransactionVO viewObjectFromEntity(TransactionDto transaction) {
        return TransactionVO.builder()
                .id(transaction.getId())
                .description(transaction.getDescription())
                .senderNodeId(transaction.getSenderNodeId())
                .receiverNodeId(transaction.getReceiverNodeId())
                .senderNodeName(transaction.getSenderName())
                .receiverNodeName(transaction.getReceiverName())
                .senderAmount(transaction.getSenderAmount())
                .receiverAmount(transaction.getReceiverAmount())
                .senderCurrencyId(transaction.getSenderCurrencyId())
                .receiverCurrencyId(transaction.getReceiverCurrencyId())
                .senderCurrencySymbol(currencyService.getById(transaction.getSenderCurrencyId()).getSymbol())
                .receiverCurrencySymbol(currencyService.getById(transaction.getReceiverCurrencyId()).getSymbol())
                .date(transaction.getDate())
                .isCancelled(transaction.isCancelled())
                .userId(transaction.getUserId())
                .isFromExternal(transaction.isFromExternal())
                .isToExternal(transaction.isToExternal())
                .build();
    }

    public TransactionDto entityFromViewObject(TransactionVO transactionVO) {
        return TransactionDto.builder()
                .id(transactionVO.getId())
                .description(transactionVO.getDescription())
                .senderNodeId(transactionVO.getSenderNodeId())
                .receiverNodeId(transactionVO.getReceiverNodeId())
                .senderAmount(transactionVO.getSenderAmount())
                .receiverAmount(transactionVO.getReceiverAmount())
                .date(transactionVO.getDate())
                .isCancelled(false)
                .userId(null)
                .build();
    }
}
