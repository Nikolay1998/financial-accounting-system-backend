package kraynov.n.financialaccountingsystembackend.mapper;

import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleNodeImpl;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;
import kraynov.n.financialaccountingsystembackend.to.NodeVO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class NodeMapper {
    private final CurrencyService currencyService;

    public NodeMapper(TransactionService transactionService,
                      CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public NodeVO viewObjectFromEntity(Node node) {
        return NodeVO.builder()
                .setId(node.getId())
                .setName(node.getName())
                .setDescription(node.getDescription())
                .setCurrencySymbol(currencyService.getById(node.getCurrencyId()).getSymbol())
                .setCurrencyId(node.getCurrencyId())
                .setAmount(node.getAmount())
                .setUserId(node.getUserId())
                .setExternal(node.isExternal())
                .setLastTransactionDate(node.getLastTransactionDate() == null ? LocalDate.MIN : node.getLastTransactionDate())
                .setOverdraft(node.isOverdraft())
                .build();
    }

    public static int compareNodeVO(NodeVO node1, NodeVO node2) {
        if (node1.getExternal() != node2.getExternal()) {
            return Boolean.compare(node1.getExternal(), node2.getExternal());
        }

        int nodeOneBancrupt = node1.getAmount().compareTo(BigDecimal.ZERO);
        int nodeTwoBancrupt = node2.getAmount().compareTo(BigDecimal.ZERO);
        if (nodeOneBancrupt != nodeTwoBancrupt) {
            return nodeTwoBancrupt - nodeOneBancrupt;
        }

        return node2.getLastTransactionDate().compareTo(node1.getLastTransactionDate());
    }

    public Node entityFromViewObject(NodeVO nodeVO) {
        return new SimpleNodeImpl.Builder()
                .setId(nodeVO.getId())
                .setName(nodeVO.getName())
                .setDescription(nodeVO.getDescription())
                .setAmount(nodeVO.getAmount())
                .setCurrencyId(nodeVO.getCurrencyId())
                .setExternal(nodeVO.getExternal())
                .setUserId(nodeVO.getUserId())
                .setOverdraft(nodeVO.getOverdraft())
                .build();
    }
}
