package kraynov.n.financialaccountingsystembackend.controller;

import kraynov.n.financialaccountingsystembackend.dto.TransactionExtendedInfoDto;
import kraynov.n.financialaccountingsystembackend.mapper.TransactionMapper;
import kraynov.n.financialaccountingsystembackend.service.FasFacade;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;
import kraynov.n.financialaccountingsystembackend.to.TransactionRequestTO;
import kraynov.n.financialaccountingsystembackend.to.TransactionResponseTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final FasFacade fasFacade;
    private final TransactionMapper transactionMapper;

    public TransactionController(TransactionService transactionService, FasFacade fasFacade,
                                 TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.fasFacade = fasFacade;
        this.transactionMapper = transactionMapper;
    }


    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponseTO add(@RequestBody TransactionRequestTO transactionVO) {
        return transactionMapper.responseFromDto(
                fasFacade.addTransaction(
                        transactionMapper.dtoFromRequest(transactionVO)));
    }


    @GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionResponseTO> getAll() {
        return transactionService.getAll().stream().map(transactionMapper::responseFromDto).toList();
    }


    @GetMapping(path = "/getAllByNode", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionResponseTO> getAllRelatedToNode(String nodeId) {
        return transactionService.getAllByNodeId(nodeId).stream().map(transactionMapper::responseFromDto).toList();
    }


    @PutMapping(path = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponseTO editTransaction(@RequestBody TransactionRequestTO transactionVO) {
        TransactionExtendedInfoDto editedTransaction = fasFacade
                .editTransaction(transactionMapper.dtoFromRequest(transactionVO));
        return transactionMapper.responseFromDto(editedTransaction);
    }


    @PutMapping(path = "/swapOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionResponseTO> swapOrder(@RequestParam String transactionId, @RequestParam String indexForSwap) {
        return transactionService.swapOrder(transactionId, indexForSwap)
                .stream()
                .map(transactionMapper::responseFromDto)
                .toList();
    }

    @GetMapping(path = "/getAllBySender", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionExtendedInfoDto> getAllBySenderId(int id) {
        // toDO: replace with transactionVO
        return transactionService.getAllBySenderId(id);
    }

    @GetMapping(path = "/getAllByReceiver")
    public List<TransactionExtendedInfoDto> getAllByReceiverId(int id) {
        // toDO: replace with transactionVO
        return transactionService.getAllByReceiverId(id);
    }


    @DeleteMapping(path = "/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TransactionResponseTO cancelTransaction(@RequestParam String transactionId) {
        TransactionExtendedInfoDto cancelledTransaction = fasFacade.cancelTransaction(transactionId);
        return transactionMapper.responseFromDto(cancelledTransaction);
    }


    @PutMapping(path = "/restore")
    public TransactionResponseTO restore(@RequestParam String transactionId) {
        TransactionExtendedInfoDto restoredTransaction = fasFacade.restoreTransaction(transactionId);
        return transactionMapper.responseFromDto(restoredTransaction);
    }
}
