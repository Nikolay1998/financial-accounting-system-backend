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
                .id(transaction.id())
                .description(transaction.description())
                .senderNodeId(transaction.senderNodeId())
                .receiverNodeId(transaction.receiverNodeId())
                .senderNodeName(transaction.senderName())
                .receiverNodeName(transaction.receiverName())
                .senderAmount(transaction.senderAmount())
                .receiverAmount(transaction.receiverAmount())
                .senderCurrencyId(transaction.senderCurrencyId())
                .receiverCurrencyId(transaction.receiverCurrencyId())
                .senderCurrencySymbol(currencyService.getById(transaction.senderCurrencyId()).symbol())
                .receiverCurrencySymbol(currencyService.getById(transaction.receiverCurrencyId()).symbol())
                .date(transaction.date())
                .isCancelled(transaction.isCancelled())
                .userId(transaction.userId())
                .isFromExternal(transaction.isFromExternal())
                .isToExternal(transaction.isToExternal())
                .order(transaction.order())
                .build();
    }

    public TransactionDto dtoFromRequest(TransactionRequestTo transactionRequestTo) {
        return TransactionDto.builder()
                .id(transactionRequestTo.id())
                .description(transactionRequestTo.description())
                .senderNodeId(transactionRequestTo.senderNodeId())
                .receiverNodeId(transactionRequestTo.receiverNodeId())
                .senderAmount(transactionRequestTo.senderAmount())
                .receiverAmount(transactionRequestTo.receiverAmount())
                .date(transactionRequestTo.date())
                .isCancelled(false)
                .userId(null)
                .build();
    }


}
