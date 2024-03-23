package kraynov.n.financialaccountingsystembackend.mapper;

import java.time.LocalDate;
import java.util.Optional;

import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.model.Transaction;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;
import kraynov.n.financialaccountingsystembackend.to.NodeVO;
import kraynov.n.financialaccountingsystembackend.utils.Currency;

public class NodeMapper {
    private final TransactionService transactionService;

    public NodeMapper(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    public NodeVO viewObjectFromEntity(Node node) {
        LocalDate lastTransactionDate = null;
        Optional<Transaction> lastTransactionByNodeId = transactionService.getLastTransactionByNodeId(node.getId());
        if (lastTransactionByNodeId.isPresent()) {
            lastTransactionDate = lastTransactionByNodeId.get().getDateTime();
        }
        return NodeVO.builder()
                .setId(node.getId())
                .setName(node.getName())
                .setDescription(node.getDescription())
                .setCurrencySymbol(Currency.define(node.getCurrencyId()).getSymbol())
                .setCurrencyId(node.getCurrencyId())
                .setAmount(node.getAmount())
                .setUserId(node.getUserId())
                .setExternal(node.isExternal())
                .setLastTransactionDate(lastTransactionDate)
                .build();
    }
}
