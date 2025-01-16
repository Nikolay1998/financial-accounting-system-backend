package kraynov.n.financialaccountingsystembackend.model.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import kraynov.n.financialaccountingsystembackend.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class SimpleTransactionImpl implements Transaction {
    private final String id;
    private final String description;
    private final String senderNodeId;
    private final String receiverNodeId;
    private final BigDecimal senderAmount;
    private final BigDecimal receiverAmount;
    private final LocalDate dateTime;
    private final boolean isCancelled;
    private final String userId;
    private final boolean isFromExternal;
    private final boolean isToExternal;


    private SimpleTransactionImpl(
            String id,
            String description,
            String senderNodeId,
            String receiverNodeId,
            BigDecimal senderAmount,
            BigDecimal receiverAmount,
            LocalDate dateTime,
            boolean isCancelled,
            String userId,
            boolean isFromExternal,
            boolean isToExternal) {
        this.id = id;
        this.description = description;
        this.senderNodeId = senderNodeId;
        this.receiverNodeId = receiverNodeId;
        this.senderAmount = senderAmount;
        this.receiverAmount = receiverAmount;
        this.dateTime = dateTime;
        this.isCancelled = isCancelled;
        this.userId = userId;
        this.isFromExternal = isFromExternal;
        this.isToExternal = isToExternal;
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

    @Override
    public boolean isFromExternal() {
        return isFromExternal;
    }

    @Override
    public boolean isToExternal() {
        return isToExternal;
    }

    @JsonFormat(pattern = "YYYY-MM-dd")
    public LocalDate getDateTime() {
        return dateTime;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SimpleTransactionImpl that = (SimpleTransactionImpl) o;
        return isCancelled == that.isCancelled &&
                isFromExternal == that.isFromExternal && isToExternal == that.isToExternal
                && Objects.equals(id, that.id) && Objects.equals(description, that.description)
                && Objects.equals(senderNodeId, that.senderNodeId) && Objects.equals(receiverNodeId, that.receiverNodeId)
                && Objects.equals(senderAmount, that.senderAmount) && Objects.equals(receiverAmount, that.receiverAmount)
                && Objects.equals(dateTime, that.dateTime) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, senderNodeId, receiverNodeId, senderAmount,
                receiverAmount, dateTime, isCancelled, userId, isFromExternal, isToExternal);
    }

    @Override
    public String toString() {
        return "SimpleTransactionImpl{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", senderNodeId='" + senderNodeId + '\'' +
                ", receiverNodeId='" + receiverNodeId + '\'' +
                ", senderAmount=" + senderAmount +
                ", receiverAmount=" + receiverAmount +
                ", dateTime=" + dateTime +
                ", isCancelled=" + isCancelled +
                ", userId='" + userId + '\'' +
                ", isFromExternal=" + isFromExternal +
                ", isToExternal=" + isToExternal +
                '}';
    }

    public static class Builder {
        private String id;
        private String description;
        private String senderNodeId;
        private String receiverNodeId;
        private BigDecimal senderAmount;
        private BigDecimal receiverAmount;
        private LocalDate dateTime;
        private boolean isCancelled;
        private String userId;
        private boolean isFromExternal;
        private boolean isToExternal;

        public Builder from(Transaction transaction) {
            this.id = transaction.getId();
            this.description = transaction.getDescription();
            this.senderNodeId = transaction.getSenderNodeId();
            this.senderAmount = transaction.getSenderAmount();
            this.receiverNodeId = transaction.getReceiverNodeId();
            this.receiverAmount = transaction.getReceiverAmount();
            this.dateTime = transaction.getDateTime();
            this.isCancelled = transaction.isCancelled();
            this.userId = transaction.getUserId();
            this.isFromExternal = transaction.isFromExternal();
            this.isToExternal = transaction.isToExternal();
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

        @JsonFormat(pattern = "YYYY-MM-dd")
        public Builder setTime(LocalDate time) {
            this.dateTime = time;
            return this;
        }

        public Builder setCancelled(boolean cancelled) {
            this.isCancelled = cancelled;
            return this;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder setFromExternal(boolean fromExternal) {
            isFromExternal = fromExternal;
            return this;
        }

        public Builder setToExternal(boolean toExternal) {
            isToExternal = toExternal;
            return this;
        }

        public Transaction build() {
            return new SimpleTransactionImpl(id,
                    description,
                    senderNodeId,
                    receiverNodeId,
                    senderAmount,
                    receiverAmount,
                    dateTime,
                    isCancelled,
                    userId,
                    isFromExternal,
                    isToExternal);
        }
    }
}
