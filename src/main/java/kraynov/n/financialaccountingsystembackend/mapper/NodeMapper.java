package kraynov.n.financialaccountingsystembackend.mapper;

import kraynov.n.financialaccountingsystembackend.dto.NodeDto;
import kraynov.n.financialaccountingsystembackend.dto.NodeExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;
import kraynov.n.financialaccountingsystembackend.to.NodeRequestTo;
import kraynov.n.financialaccountingsystembackend.to.NodeResponseTo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class NodeMapper {
    private final CurrencyService currencyService;

    public NodeMapper(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public static int compareNodeVO(
            NodeResponseTo node1,
            NodeResponseTo node2
    ) {
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

    public NodeResponseTo responseFromDto(NodeExtendedInfoDto node) {
        return NodeResponseTo.builder()
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

    public NodeDto dtoFromRequest(NodeRequestTo nodeRequestTo) {
        return NodeDto.builder()
                .id(nodeRequestTo.getId())
                .name(nodeRequestTo.getName())
                .description(nodeRequestTo.getDescription())
                .amount(nodeRequestTo.getAmount())
                .currencyId(nodeRequestTo.getCurrencyId())
                .isExternal(nodeRequestTo.isExternal())
                .userId(nodeRequestTo.getUserId())
                .isOverdraft(nodeRequestTo.isOverdraft())
                .isArchived(nodeRequestTo.isArchived())
                .build();
    }
}
