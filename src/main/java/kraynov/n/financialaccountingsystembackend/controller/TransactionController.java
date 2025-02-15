package kraynov.n.financialaccountingsystembackend.controller;

import kraynov.n.financialaccountingsystembackend.exception.AlreadyCanceledException;
import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;
import kraynov.n.financialaccountingsystembackend.mapper.TransactionMapper;
import kraynov.n.financialaccountingsystembackend.model.TransactionDto;
import kraynov.n.financialaccountingsystembackend.service.FasFacade;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;
import kraynov.n.financialaccountingsystembackend.to.TransactionVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionVO add(@RequestBody TransactionVO transaction) throws InsufficientFundsException {
        return transactionMapper.viewObjectFromEntity(fasFacade.addTransaction(transactionMapper.entityFromViewObject(transaction)));
    }

    @CrossOrigin
    @GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionVO> getAll() {
        return transactionService.getAll().stream().map(transactionMapper::viewObjectFromEntity).toList();
    }

    @CrossOrigin
    @GetMapping(path = "/getAllByNode", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionVO> getAllRelatedToNode(String nodeId) {
        return transactionService.getAllByNodeId(nodeId).stream().map(transactionMapper::viewObjectFromEntity).toList();
    }

    @CrossOrigin
    @PutMapping(path = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TransactionVO editTransaction(@RequestBody TransactionVO transactionVO) throws InsufficientFundsException {
        TransactionDto editedTransaction = fasFacade
                .editTransaction(transactionMapper.entityFromViewObject(transactionVO));
        return transactionMapper.viewObjectFromEntity(editedTransaction);
    }

    @GetMapping(path = "/getAllBySender", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TransactionDto> getAllBySenderId(int id) {
        // toDO: replace with transactionVO
        return transactionService.getAllBySenderId(id);
    }

    @GetMapping(path = "/getAllByReceiver")
    public List<TransactionDto> getAllByReceiverId(int id) {
        // toDO: replace with transactionVO
        return transactionService.getAllByReceiverId(id);
    }

    @CrossOrigin
    @DeleteMapping(path = "/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    public TransactionVO cancelTransaction(@RequestParam String transactionId) throws InsufficientFundsException, AlreadyCanceledException {
        TransactionDto cancelledTransaction = fasFacade.cancelTransaction(transactionId);
        return transactionMapper.viewObjectFromEntity(cancelledTransaction);
    }

    @CrossOrigin
    @PutMapping(path = "/restore")
    public TransactionVO restore(@RequestParam String transactionId) throws InsufficientFundsException {
        TransactionDto restoredTransaction = fasFacade.restoreTransaction(transactionId);
        return transactionMapper.viewObjectFromEntity(restoredTransaction);
    }
}
