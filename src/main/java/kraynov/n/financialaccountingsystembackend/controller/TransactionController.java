package kraynov.n.financialaccountingsystembackend.controller;

import kraynov.n.financialaccountingsystembackend.exception.InsufficientFundsException;
import kraynov.n.financialaccountingsystembackend.model.Transaction;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleTransactionImpl;
import kraynov.n.financialaccountingsystembackend.service.FASFacade;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final FASFacade fasFacade;

    public TransactionController(TransactionService transactionService, FASFacade fasFacade) {
        this.transactionService = transactionService;
        this.fasFacade = fasFacade;
    }

    @CrossOrigin
    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction add(@RequestBody SimpleTransactionImpl transaction) throws InsufficientFundsException {
        return fasFacade.addTransaction(transaction);
    }

    @CrossOrigin
    @GetMapping(path = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Transaction> getAll() {
        return transactionService.getAll();
    }

    @GetMapping(path = "/getAllBySender", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Transaction> getAllBySenderId(int id) {
        return transactionService.getAllBySenderId(id);
    }

    @GetMapping(path = "/getAllByReceiver")
    public List<Transaction> getAllByReceiverId(int id) {
        return transactionService.getAllByReceiverId(id);
    }

    @CrossOrigin
    @DeleteMapping(path = "/cancel", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Transaction cancelTransaction(@RequestBody String transactionId) throws InsufficientFundsException {
        return fasFacade.cancelTransaction(transactionId);
    }
}
