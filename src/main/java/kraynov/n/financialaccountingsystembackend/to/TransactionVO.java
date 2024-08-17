package kraynov.n.financialaccountingsystembackend.to;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionVO {
    private final String id;
    private final String description;
    private final String senderNodeId;
    private final String receiverNodeId;
    private final String senderNodeName;
    private final String receiverNodeName;
    private final BigDecimal senderAmount;
    private final BigDecimal receiverAmount;
    private final String senderCurrencyId;
    private final String senderCurrencySymbol;
    private final String receiverCurrencyId;
    private final String receiverCurrencySymbol;
    private final LocalDate date;
    private final boolean isCancelled;
    private final String userId;

    public TransactionVO(String id, String description, String senderNodeId, String receiverNodeId,
            String senderNodeName, String receiverNodeName, BigDecimal senderAmount, BigDecimal receiverAmount,
            String senderCurrencyId, String senderCurrencySymbol, String receiverCurrencyId, String receiverCurrencySymbol,
            LocalDate date, boolean isCancelled, String userId) {
        this.id = id;
        this.description = description;
        this.senderNodeId = senderNodeId;
        this.receiverNodeId = receiverNodeId;
        this.senderNodeName = senderNodeName;
        this.receiverNodeName = receiverNodeName;
        this.senderAmount = senderAmount;
        this.receiverAmount = receiverAmount;
        this.senderCurrencyId = senderCurrencyId;
        this.senderCurrencySymbol = senderCurrencySymbol;
        this.receiverCurrencyId = receiverCurrencyId;
        this.receiverCurrencySymbol = receiverCurrencySymbol;
        this.date = date;
        this.isCancelled = isCancelled;
        this.userId = userId;
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

    public String getSenderNodeName() {
        return senderNodeName;
    }

    public String getReceiverNodeName() {
        return receiverNodeName;
    }

    public BigDecimal getSenderAmount() {
        return senderAmount;
    }

    public BigDecimal getReceiverAmount() {
        return receiverAmount;
    }

    public String getSenderCurrencyId() {
        return senderCurrencyId;
    }

    public String getSenderCurrencySymbol() {
        return senderCurrencySymbol;
    }

    public String getReceiverCurrencyId() {
        return receiverCurrencyId;
    }

    public String getReceiverCurrencySymbol() {
        return receiverCurrencySymbol;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public String getUserId() {
        return userId;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String id;
        private String description;
        private String senderNodeId;
        private String receiverNodeId;
        private String senderNodeName;
        private String receiverNodeName;
        private BigDecimal senderAmount;
        private BigDecimal receiverAmount;
        private String senderCurrencyId;
        private String senderCurrencySymbol;
        private String receiverCurrencyId;
        private String receiverCurrencySymbol;
        private LocalDate date;
        private boolean isCancelled;
        private String userId;

        public String getId() {
            return id;
        }

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public String getDescription() {
            return description;
        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;
        }

        public String getSenderNodeId() {
            return senderNodeId;
        }

        public Builder setSenderNodeId(String senderNodeId) {
            this.senderNodeId = senderNodeId;
            return this;
        }

        public String getReceiverNodeId() {
            return receiverNodeId;
        }

        public Builder setReceiverNodeId(String receiverNodeId) {
            this.receiverNodeId = receiverNodeId;
            return this;
        }

        public String getSenderNodeName() {
            return senderNodeName;
        }

        public Builder setSenderNodeName(String senderNodeName) {
            this.senderNodeName = senderNodeName;
            return this;
        }

        public String getReceiverNodeName() {
            return receiverNodeName;
        }

        public Builder setReceiverNodeName(String receiverNodeName) {
            this.receiverNodeName = receiverNodeName;
            return this;
        }

        public BigDecimal getSenderAmount() {
            return senderAmount;
        }

        public Builder setSenderAmount(BigDecimal senderAmount) {
            this.senderAmount = senderAmount;
            return this;
        }

        public BigDecimal getReceiverAmount() {
            return receiverAmount;
        }

        public Builder setReceiverAmount(BigDecimal receiverAmount) {
            this.receiverAmount = receiverAmount;
            return this;
        }

        public String getSenderCurrencyId() {
            return senderCurrencyId;
        }

        public Builder setSenderCurrencyId(String senderCurrencyId) {
            this.senderCurrencyId = senderCurrencyId;
            return this;
        }

        public String getSenderCurrencySymbol() {
            return senderCurrencySymbol;
        }

        public Builder setSenderCurrencySymbol(String senderCurrencySymbol) {
            this.senderCurrencySymbol = senderCurrencySymbol;
            return this;
        }

        public String getReceiverCurrencyId() {
            return receiverCurrencyId;
        }

        public Builder setReceiverCurrencyId(String receiverCurrencyId) {
            this.receiverCurrencyId = receiverCurrencyId;
            return this;
        }

        public String getReceiverCurrencySymbol() {
            return receiverCurrencySymbol;
        }

        public Builder setReceiverCurrencySymbol(String receiverCurrencySymbol) {
            this.receiverCurrencySymbol = receiverCurrencySymbol;
            return this;
        }

        public LocalDate getDate() {
            return date;
        }

        public Builder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public boolean isCancelled() {
            return isCancelled;
        }

        public Builder setCancelled(boolean isCancelled) {
            this.isCancelled = isCancelled;
            return this;
        }

        public String getUserId() {
            return userId;
        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public TransactionVO build() {
            return new TransactionVO(id, description, senderNodeId, receiverNodeId, senderNodeName, receiverNodeName,
                    senderAmount, receiverAmount, senderCurrencyId, senderCurrencySymbol, receiverCurrencyId,
                    receiverCurrencySymbol, date, isCancelled, userId);
        }

    }

}
