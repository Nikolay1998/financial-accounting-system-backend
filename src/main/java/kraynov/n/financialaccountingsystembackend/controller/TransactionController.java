package kraynov.n.financialaccountingsystembackend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;
import kraynov.n.financialaccountingsystembackend.mapper.TransactionMapper;
import kraynov.n.financialaccountingsystembackend.model.Transaction;
import kraynov.n.financialaccountingsystembackend.service.FASFacade;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;
import kraynov.n.financialaccountingsystembackend.to.TransactionVO;

@RestController
@RequestMapping(path = "/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final FASFacade fasFacade;
    private final TransactionMapper transactionMapper;

    public TransactionController(TransactionService transactionService, FASFacade fasFacade,
            TransactionMapper transactionMapper) {
        this.transactionService = transactionService;
        this.fasFacade = fasFacade;
        this.transactionMapper = transactionMapper;
    }

    @CrossOrigin
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction add(@RequestBody TransactionVO transaction) throws InsufficientFundsException {
        return fasFacade.addTransaction(transactionMapper.entityFromViewObject(transaction));
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
        Transaction editedTransaction = fasFacade
                .editTransaction(transactionMapper.entityFromViewObject(transactionVO));
        return transactionMapper.viewObjectFromEntity(editedTransaction);
    }

    @GetMapping(path = "/getAllBySender", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Transaction> getAllBySenderId(int id) {
        // toDO: replace with transactionVO
        return transactionService.getAllBySenderId(id);
    }

    @GetMapping(path = "/getAllByReceiver")
    public List<Transaction> getAllByReceiverId(int id) {
        // toDO: replace with transactionVO
        return transactionService.getAllByReceiverId(id);
    }

    @CrossOrigin
    @DeleteMapping(path = "/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Transaction cancelTransaction(@RequestBody String transactionId) throws InsufficientFundsException {
        // toDO: replace with transactionVO
        return fasFacade.cancelTransaction(transactionId);
    }
}
