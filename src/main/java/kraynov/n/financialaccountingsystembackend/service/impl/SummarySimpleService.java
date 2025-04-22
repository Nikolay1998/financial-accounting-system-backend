package kraynov.n.financialaccountingsystembackend.service.impl;

import kraynov.n.financialaccountingsystembackend.dao.NodeDAO;
import kraynov.n.financialaccountingsystembackend.dto.NodeExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.dto.TransactionExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.dto.TransactionFilterDto;
import kraynov.n.financialaccountingsystembackend.dto.UserDetailsDto;
import kraynov.n.financialaccountingsystembackend.security.ContextHolderFacade;
import kraynov.n.financialaccountingsystembackend.service.CurrencyService;
import kraynov.n.financialaccountingsystembackend.service.SummaryService;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;
import kraynov.n.financialaccountingsystembackend.to.CurrencyBalanceChangeTo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class SummarySimpleService implements SummaryService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SummarySimpleService.class);
    private final NodeDAO nodeDAO;
    private final TransactionService transactionService;
    private final ContextHolderFacade contextHolderFacade;
    private final CurrencyService currencyService;

    public SummarySimpleService(NodeDAO nodeDAO,
                                TransactionService transactionService,
                                ContextHolderFacade contextHolderFacade,
                                CurrencyService currencyService) {
        this.nodeDAO = nodeDAO;
        this.transactionService = transactionService;
        this.contextHolderFacade = contextHolderFacade;
        this.currencyService = currencyService;
    }

    @Override
    public Map<String, BigDecimal> getSum() {
        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();
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

    @Override
    public List<CurrencyBalanceChangeTo> getBalanceChange(LocalDate from, LocalDate to) {
        UserDetailsDto userDTO = contextHolderFacade.getAuthenticatedUserOrThrowException();

        LOGGER.debug("Start computing in and out for user with id = {}", userDTO.getId());
        List<TransactionExtendedInfoDto> transactionsByFilter = transactionService.getAllByFilter(
                TransactionFilterDto.builder()
                        .to(to)
                        .from(from)
                        .build());

        Map<String, BigDecimal> in = new HashMap<>();
        Map<String, BigDecimal> out = new HashMap<>();

        for (TransactionExtendedInfoDto transaction : transactionsByFilter) {
            if (transaction.isFromExternal()) {
                in.merge(transaction.getReceiverCurrencyId(), transaction.getReceiverAmount(), BigDecimal::add);
            } else if (transaction.isToExternal()) {
                out.merge(transaction.getSenderCurrencyId(), transaction.getSenderAmount(), BigDecimal::add);
            } else if (!transaction.getSenderCurrencyId().equals(transaction.getReceiverCurrencyId())) {
                in.merge(transaction.getReceiverCurrencyId(), transaction.getReceiverAmount(), BigDecimal::add);
                out.merge(transaction.getSenderCurrencyId(), transaction.getSenderAmount(), BigDecimal::add);
            }
        }

        Set<String> combinedCurrencyIds = new HashSet<>();
        combinedCurrencyIds.addAll(in.keySet());
        combinedCurrencyIds.addAll(out.keySet());

        List<CurrencyBalanceChangeTo> result = new ArrayList<>();
        for (String currencyId : combinedCurrencyIds) {
            BigDecimal income = Optional.ofNullable(in.get(currencyId)).orElse(BigDecimal.ZERO);
            BigDecimal outgo = Optional.ofNullable(out.get(currencyId)).orElse(BigDecimal.ZERO);
            result.add(CurrencyBalanceChangeTo.builder()
                    .currencyId(currencyId)
                    .income(income)
                    .outgo(outgo)
                    .totalChange(income.subtract(outgo))
                    .build());
        }

        return result;
    }
}
