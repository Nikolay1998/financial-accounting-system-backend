package kraynov.n.financialaccountingsystembackend.to;

import java.math.BigDecimal;
import java.time.LocalDate;

public class NodeVO {

    private final String id;

    private final String name;

    private final String description;

    private final String currencySymbol;

    private final String currencyId;

    private final BigDecimal amount;

    private final String userId;

    private final boolean external;

    private final LocalDate lastTransactionDate;

    private final boolean overdraft;

    public NodeVO(String id, String name, String description, String currencySymbol, String currencyId, BigDecimal amount,
                  String userId, boolean external, LocalDate lastTransactionDate, boolean overdraft) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currencySymbol = currencySymbol;
        this.currencyId = currencyId;
        this.amount = amount;
        this.userId = userId;
        this.external = external;
        this.lastTransactionDate = lastTransactionDate;
        this.overdraft = overdraft;
    }

    public static Builder builder() {
        return new Builder();
    }

    

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getUserId() {
        return userId;
    }

    public boolean getExternal() {
        return external;
    }

    public LocalDate getLastTransactionDate() {
        return lastTransactionDate;
    }

    public boolean getOverdraft() {
        return overdraft;
    }

    public static class Builder {

        private String id;

        private String name;

        private String description;

        private String currencySymbol;

        private String currencyId;

        private BigDecimal amount;

        private String userId;

        private boolean isExternal;

        private LocalDate lastTransactionDate;

        private boolean overdraft;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;

        }

        public Builder setDescription(String description) {
            this.description = description;
            return this;

        }

        public Builder setCurrencySymbol(String currencySymbol) {
            this.currencySymbol = currencySymbol;
            return this;

        }

        public Builder setCurrencyId(String currencyId) {
            this.currencyId = currencyId;
            return this;

        }

        public Builder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;

        }

        public Builder setUserId(String userId) {
            this.userId = userId;
            return this;

        }

        public Builder setExternal(boolean isExternal) {
            this.isExternal = isExternal;
            return this;

        }

        public Builder setLastTransactionDate(LocalDate lastTransactionDate) {
            this.lastTransactionDate = lastTransactionDate;
            return this;

        }

        public Builder setOverdraft(boolean overdraft) {
            this.overdraft = overdraft;
            return this;
        }

        public NodeVO build() {
            return new NodeVO(id, name, description, currencySymbol, currencyId, amount, userId, isExternal,
                    lastTransactionDate, overdraft);
        }

    }

}