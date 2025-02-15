package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.model.NodeExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.model.UserDetailsDto;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;
import kraynov.n.financialaccountingsystembackend.service.SummaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SummarySimpleService implements SummaryService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SummarySimpleService.class);
    private final NodeDAO nodeDAO;
    private final ContextHolderFacade contextHolderFacade;
    private final CurrencyService currencyService;

    public SummarySimpleService(NodeDAO nodeDAO, ContextHolderFacade contextHolderFacade,
            CurrencyService currencyService) {
        this.nodeDAO = nodeDAO;
        this.contextHolderFacade = contextHolderFacade;
        this.currencyService = currencyService;
    }

    @Override
    public Map<String, BigDecimal> getSum() {
        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUser();
        if (userDTO == null) {
            throw new IllegalStateException();
        }
        LOGGER.debug("Start computing sum for user with id = {}", userDTO.getId());
        List<NodeExtendedInfoDto> nodes = nodeDAO.getAll(userDTO.getId()).stream().filter(n -> !n.isExternal()).toList();
        Map<String, BigDecimal> sum = new HashMap<>();
        for (NodeExtendedInfoDto node : nodes) {
            sum.merge(node.getCurrencyId(), node.getAmount(), BigDecimal::add);
        }
        Map<String, BigDecimal> sumWithCurencySymbol = sum.entrySet().stream().collect(Collectors.toMap(
                e -> currencyService.getById(e.getKey()).getSymbol(),
                Map.Entry::getValue));

        LOGGER.debug("End computing sum for user with id = {}, sum = {}", userDTO.getId(), sumWithCurencySymbol);
        return sumWithCurencySymbol;
    }
}
