package kraynov.n.financialaccountingsystembackend.controller;

import kraynov.n.financialaccountingsystembackend.model.Transaction;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleTransactionImpl;
import kraynov.n.financialaccountingsystembackend.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/transaction")
public class TransactionController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(path = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction add(@RequestBody SimpleTransactionImpl transaction) {
        return transactionService.add(transaction);
    }

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
}
