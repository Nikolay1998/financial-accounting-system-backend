package kraynov.n.financialaccountingsystembackend.utils;

import kraynov.n.financialaccountingsystembackend.model.Node;
import kraynov.n.financialaccountingsystembackend.model.Transaction;
import kraynov.n.financialaccountingsystembackend.model.UserDTO;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleNodeImpl;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleTransactionImpl;
import kraynov.n.financialaccountingsystembackend.model.impl.SimpleUserDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class TestHelper {
    private final AtomicInteger transactionCounter = new AtomicInteger(0);

    private final AtomicInteger nodeCounter = new AtomicInteger(0);

    private final UserDTO testUser = new SimpleUserDTO("TEST_USER_ID", "name", "123");

    public UserDTO getTestUser() {
        return testUser;
    }

    public Transaction createTransaction(String senderNodeId, String receiverNodeId, BigDecimal amount) {
        return SimpleTransactionImpl.builder()
                .setId(String.valueOf(transactionCounter.incrementAndGet()))
                .setDescription("description")
                .setSenderNodeId(senderNodeId)
                .setReceiverNodeId(receiverNodeId)
                .setSenderAmount(amount)
                .setReceiverAmount(amount)
                .setTime(LocalDate.from(LocalDateTime.now()))
                .setCancelled(false)
                .setUserId(testUser.getId())
                .build();
    }

    public Node createNode(BigDecimal amount, boolean isExternal) {
        return new SimpleNodeImpl.Builder()
                .setId(String.valueOf(nodeCounter.incrementAndGet()))
                .setAmount(amount)
                .setCurrencyId(1)
                .setDescription("Just node")
                .setExternal(isExternal)
                .setName("Node")
                .setUserId(testUser.getId())
                .build();
    }
}
