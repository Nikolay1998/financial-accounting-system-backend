package kraynov.n.financialaccountingsystembackend.utils;

import kraynov.n.financialaccountingsystembackend.dto.NodeDto;
import kraynov.n.financialaccountingsystembackend.dto.TransactionDto;
import kraynov.n.financialaccountingsystembackend.dto.UserDetailsDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class TestHelper {
    private final AtomicInteger transactionCounter = new AtomicInteger(0);

    private final AtomicInteger nodeCounter = new AtomicInteger(0);

    private final UserDetailsDto testUser = new UserDetailsDto("TEST_USER_ID", "name", "123");

    public UserDetailsDto getTestUser() {
        return testUser;
    }

    public TransactionDto createTransactionDto(String senderNodeId, String receiverNodeId, BigDecimal amount) {
        return TransactionDto.builder()
                .id(String.valueOf(transactionCounter.incrementAndGet()))
                .description("description")
                .senderNodeId(senderNodeId)
                .receiverNodeId(receiverNodeId)
                .senderAmount(amount)
                .receiverAmount(amount)
                .date(LocalDate.from(LocalDateTime.now()))
                .isCancelled(false)
                .userId(testUser.getId())
                .build();
    }

    public NodeDto createNode(BigDecimal amount, boolean isExternal) {
        return NodeDto.builder()
                .id(String.valueOf(nodeCounter.incrementAndGet()))
                .amount(amount)
                .currencyId("1")
                .description("Just node")
                .isExternal(isExternal)
                .name("Node")
                .userId(testUser.getId())
                .build();
    }
}
