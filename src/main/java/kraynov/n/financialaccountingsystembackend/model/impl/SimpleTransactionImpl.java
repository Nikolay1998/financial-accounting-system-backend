package kraynov.n.financialaccountingsystembackend.model.impl;

import kraynov.n.financialaccountingsystembackend.model.Transaction;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SimpleTransactionImpl implements Transaction {
    private final String id;
    private final String description;
    private final String senderNodeId;
    private final String receiverNodeId;
    private final BigDecimal senderAmount;
    private final BigDecimal receiverAmount;
    private final Timestamp time;

    public SimpleTransactionImpl(String id, String description, String senderNodeId, String receiverNodeId, BigDecimal senderAmount, BigDecimal receiverAmount, Timestamp time) {
        this.id = id;
        this.description = description;
        this.senderNodeId = senderNodeId;
        this.receiverNodeId = receiverNodeId;
        this.senderAmount = senderAmount;
        this.receiverAmount = receiverAmount;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getSenderNodeId() {
        return senderNodeId;
    }

    public String getReceiverNodeId() {
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

    public static class Builder {
        private String id;
        private String description;
        private String senderNodeId;
        private String receiverNodeId;
        private BigDecimal senderAmount;
        private BigDecimal receiverAmount;
        private Timestamp time;

        public Builder from(Transaction transaction) {
            this.id = transaction.getId();
            this.description = transaction.getDescription();
            this.senderNodeId = transaction.getSenderNodeId();
            this.senderAmount = transaction.getSenderAmount();
            this.receiverNodeId = transaction.getReceiverNodeId();
            this.receiverAmount = transaction.getReceiverAmount();
            this.time = transaction.getTime();
            return this;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder setSenderNodeId(String senderNodeId) {
            this.senderNodeId = senderNodeId;
            return this;
        }

        public Builder setReceiverNodeId(String receiverNodeId) {
            this.receiverNodeId = receiverNodeId;
            return this;
        }

        public Builder setSenderAmount(BigDecimal senderAmount) {
            this.senderAmount = senderAmount;
            return this;
        }

        public Builder setReceiverAmount(BigDecimal receiverAmount) {
            this.receiverAmount = receiverAmount;
            return this;
        }

        public Builder setTime(Timestamp time) {
            this.time = time;
            return this;
        }

        public Transaction build() {
            return new SimpleTransactionImpl(id, description, senderNodeId, receiverNodeId, senderAmount, receiverAmount, time);
        }
    }
}
