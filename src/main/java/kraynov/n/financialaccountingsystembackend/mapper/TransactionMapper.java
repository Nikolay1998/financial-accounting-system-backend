package kraynov.n.financialaccountingsystembackend.mapper;

import kraynov.n.financialaccountingsystembackend.dto.TransactionDto;
import kraynov.n.financialaccountingsystembackend.dto.TransactionExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;
import kraynov.n.financialaccountingsystembackend.to.TransactionRequestTo;
import kraynov.n.financialaccountingsystembackend.to.TransactionResponseTo;

public class TransactionMapper {
    private final CurrencyService currencyService;

    public TransactionMapper(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public TransactionResponseTo responseFromDto(TransactionExtendedInfoDto transaction) {
        return TransactionResponseTo.builder()
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
                .order(transaction.getOrder())
                .build();
    }

    public TransactionDto dtoFromRequest(TransactionRequestTo transactionRequestTo) {
        return TransactionDto.builder()
                .id(transactionRequestTo.getId())
                .description(transactionRequestTo.getDescription())
                .senderNodeId(transactionRequestTo.getSenderNodeId())
                .receiverNodeId(transactionRequestTo.getReceiverNodeId())
                .senderAmount(transactionRequestTo.getSenderAmount())
                .receiverAmount(transactionRequestTo.getReceiverAmount())
                .date(transactionRequestTo.getDate())
                .isCancelled(false)
                .userId(null)
                .build();
    }


}
