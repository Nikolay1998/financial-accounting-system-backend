package kraynov.n.financialaccountingsystembackend.mapper;

import kraynov.n.financialaccountingsystembackend.dto.NodeDto;
import kraynov.n.financialaccountingsystembackend.dto.NodeExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;
import kraynov.n.financialaccountingsystembackend.to.NodeRequestTO;
import kraynov.n.financialaccountingsystembackend.to.NodeResponseTO;

import java.math.BigDecimal;
import java.time.LocalDate;

public class NodeMapper {
    private final CurrencyService currencyService;

    public NodeMapper(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public static int compareNodeVO(NodeResponseTO node1, NodeResponseTO node2) {
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

    public NodeResponseTO responseFromDto(NodeExtendedInfoDto node) {
        return NodeResponseTO.builder()
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

    public NodeDto dtoFromRequest(NodeRequestTO nodeRequestTO) {
        return NodeDto.builder()
                .id(nodeRequestTO.getId())
                .name(nodeRequestTO.getName())
                .description(nodeRequestTO.getDescription())
                .amount(nodeRequestTO.getAmount())
                .currencyId(nodeRequestTO.getCurrencyId())
                .isExternal(nodeRequestTO.isExternal())
                .userId(nodeRequestTO.getUserId())
                .isOverdraft(nodeRequestTO.isOverdraft())
                .isArchived(nodeRequestTO.isArchived())
                .build();
    }
}
