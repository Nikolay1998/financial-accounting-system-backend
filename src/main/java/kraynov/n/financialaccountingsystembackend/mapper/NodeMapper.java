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

        int nodeOneBancrupt = node1.amount().compareTo(BigDecimal.ZERO);
        int nodeTwoBancrupt = node2.amount().compareTo(BigDecimal.ZERO);
        if (nodeOneBancrupt != nodeTwoBancrupt) {
            return nodeTwoBancrupt - nodeOneBancrupt;
        }

        return node2.lastTransactionDate().compareTo(node1.lastTransactionDate());
    }

    public NodeResponseTo responseFromDto(NodeExtendedInfoDto node) {
        return NodeResponseTo.builder()
                .id(node.id())
                .name(node.name())
                .description(node.description())
                .currencySymbol(currencyService.getById(node.currencyId()).symbol())
                .currencyId(node.currencyId())
                .amount(node.amount())
                .userId(node.userId())
                .isExternal(node.isExternal())
                .lastTransactionDate(node.lastTransactionDate() == null ? LocalDate.MIN : node.lastTransactionDate())
                .isOverdraft(node.isOverdraft())
                .isArchived(node.isArchived())
                .build();
    }

    public NodeDto dtoFromRequest(NodeRequestTo nodeRequestTo) {
        return NodeDto.builder()
                .id(nodeRequestTo.id())
                .name(nodeRequestTo.name())
                .description(nodeRequestTo.description())
                .amount(nodeRequestTo.amount())
                .currencyId(nodeRequestTo.currencyId())
                .isExternal(nodeRequestTo.isExternal())
                .userId(nodeRequestTo.userId())
                .isOverdraft(nodeRequestTo.isOverdraft())
                .isArchived(nodeRequestTo.isArchived())
                .build();
    }
}
