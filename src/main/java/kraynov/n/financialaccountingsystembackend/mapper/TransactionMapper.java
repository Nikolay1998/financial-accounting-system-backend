package kraynov.n.financialaccountingsystembackend.mapper;

import kraynov.n.financialaccountingsystembackend.dto.TransactionDto;
import kraynov.n.financialaccountingsystembackend.dto.TransactionExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;
import kraynov.n.financialaccountingsystembackend.to.TransactionRequestTO;
import kraynov.n.financialaccountingsystembackend.to.TransactionResponseTO;

public class TransactionMapper {
    private final CurrencyService currencyService;

    public TransactionMapper(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public TransactionResponseTO responseFromDto(TransactionExtendedInfoDto transaction) {
        return TransactionResponseTO.builder()
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

    public TransactionDto dtoFromRequest(TransactionRequestTO transactionRequestTO) {
        return TransactionDto.builder()
                .id(transactionRequestTO.getId())
                .description(transactionRequestTO.getDescription())
                .senderNodeId(transactionRequestTO.getSenderNodeId())
                .receiverNodeId(transactionRequestTO.getReceiverNodeId())
                .senderAmount(transactionRequestTO.getSenderAmount())
                .receiverAmount(transactionRequestTO.getReceiverAmount())
                .date(transactionRequestTO.getDate())
                .isCancelled(false)
                .userId(null)
                .build();
    }


}
