package kraynov.n.financialaccountingsystembackend.mapper;

import kraynov.n.financialaccountingsystembackend.model.NodeDto;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;
import kraynov.n.financialaccountingsystembackend.to.NodeVO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class NodeMapper {
    private final CurrencyService currencyService;

    public NodeMapper(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public static int compareNodeVO(NodeVO node1, NodeVO node2) {
        if (node1.isExternal() != node2.isExternal()) {
            return Boolean.compare(node1.isExternal(), node2.isExternal());
        }

        int nodeOneBancrupt = node1.getAmount().compareTo(BigDecimal.ZERO);
        int nodeTwoBancrupt = node2.getAmount().compareTo(BigDecimal.ZERO);
        if (nodeOneBancrupt != nodeTwoBancrupt) {
            return nodeTwoBancrupt - nodeOneBancrupt;
        }

        return node2.getLastTransactionDate().compareTo(node1.getLastTransactionDate());
    }

    public NodeVO viewObjectFromEntity(NodeDto node) {
        return NodeVO.builder()
                .id(node.getId())
                .name(node.getName())
                .description(node.getDescription())
                .currencySymbol(currencyService.getById(node.getCurrencyId()).getSymbol())
                .currencyId(node.getCurrencyId())
                .amount(node.getAmount())
                .userId(node.getUserId())
                .isExternal(node.isExternal())
                .lastTransactionDate(node.getLastTransactionDate() == null ? LocalDate.MIN : node.getLastTransactionDate())
                .isOverdraft(node.isOverdraft())
                .isArchived(node.isArchived())
                .build();
    }

    public NodeDto entityFromViewObject(NodeVO nodeVO) {
        return NodeDto.builder()
                .id(nodeVO.getId())
                .name(nodeVO.getName())
                .description(nodeVO.getDescription())
                .amount(nodeVO.getAmount())
                .currencyId(nodeVO.getCurrencyId())
                .isExternal(nodeVO.isExternal())
                .userId(nodeVO.getUserId())
                .isOverdraft(nodeVO.isOverdraft())
                .isArchived(nodeVO.isArchived())
                .build();
    }
}
