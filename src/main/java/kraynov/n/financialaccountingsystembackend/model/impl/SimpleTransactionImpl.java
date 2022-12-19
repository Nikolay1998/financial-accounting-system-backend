package kraynov.n.financialaccountingsystembackend.model.impl;

import kraynov.n.financialaccountingsystembackend.model.Transaction;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SimpleTransactionImpl implements Transaction {
    private final int id;
    private final String description;
    private final int senderNodeId;
    private final int receiverNodeId;
    private final BigDecimal senderAmount;
    private final BigDecimal receiverAmount;
    private final Timestamp time;

    public SimpleTransactionImpl(int id, String description, int senderNodeId, int receiverNodeId, BigDecimal senderAmount, BigDecimal receiverAmount, Timestamp time) {
        this.id = id;
        this.description = description;
        this.senderNodeId = senderNodeId;
        this.receiverNodeId = receiverNodeId;
        this.senderAmount = senderAmount;
        this.receiverAmount = receiverAmount;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getSenderNodeId() {
        return senderNodeId;
    }

    public int getReceiverNodeId() {
        return receiverNodeId;
    }

    public BigDecimal getSenderAmount() {
        return senderAmount;
    }

    public BigDecimal getReceiverAmount() {
        return receiverAmount;
    }

    public Timestamp getTime() {
        return time;
    }
}
