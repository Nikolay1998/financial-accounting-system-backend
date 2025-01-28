package kraynov.n.financialaccountingsystembackend.model.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import kraynov.n.financialaccountingsystembackend.model.Node;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class SimpleNodeImpl implements Node {
    private final String id;
    private final String name;
    private final String description;
    private final String currencyId;
    private final BigDecimal amount;
    private final String userId;
    private final boolean isExternal;
    private final LocalDate lastTransactionDate;
    private final boolean isOverdraft;

    private SimpleNodeImpl(String id, String name, String description, String currencyId, BigDecimal amount, String userId, boolean external, LocalDate lastTransactionDate, boolean isOverdraft) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currencyId = currencyId;
        this.amount = amount;
        this.userId = userId;
        this.isExternal = external;
        this.lastTransactionDate = lastTransactionDate;
        this.isOverdraft = isOverdraft;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getCurrencyId() {
        return currencyId;
    }

    @Override
    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public boolean isExternal() {
        return isExternal;
    }

    @Override
    public LocalDate getLastTransactionDate() {
        return lastTransactionDate;
    }

    @Override
    public boolean isOverdraft() {
        return isOverdraft;
    }

    @Override
    public String toString() {
        return "SimpleNodeImpl{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", currencyId='" + currencyId + '\'' +
                ", amount=" + amount +
                ", userId='" + userId + '\'' +
                ", isExternal=" + isExternal +
                ", lastTransactionDate=" + lastTransactionDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        SimpleNodeImpl that = (SimpleNodeImpl) o;
        return isExternal == that.isExternal && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(currencyId, that.currencyId) && Objects.equals(amount, that.amount) && Objects.equals(userId, that.userId) && Objects.equals(lastTransactionDate, that.lastTransactionDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, currencyId, amount, userId, isExternal, lastTransactionDate);
    }

    public static class Builder {

        private String id;
        private String name;
        private String description;
        private String currencyId;
        private BigDecimal amount;
        private String userId;
        private boolean isExternal;
        private LocalDate lastTransactionDate;
        private boolean isOverdraft;

        public Builder from(Node node) {
            this.id = node.getId();
            this.name = node.getName();
            this.description = node.getDescription();
            this.currencyId = node.getCurrencyId();
            this.amount = node.getAmount();
            this.userId = node.getUserId();
            this.isExternal = node.isExternal();
            this.lastTransactionDate = node.getLastTransactionDate();
            this.isOverdraft = node.isOverdraft();
            return this;
        }

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

        public Builder setExternal(boolean external) {
            isExternal = external;
            return this;
        }

        public Builder setOverdraft(boolean overdraft) {
            isOverdraft = overdraft;
            return this;
        }

        @JsonFormat(pattern = "YYYY-MM-dd")
        public Builder setLastTransactionDate(LocalDate lastTransactionDate) {
            this.lastTransactionDate = lastTransactionDate;
            return this;
        }

        public Node build() {
            return new SimpleNodeImpl(id, name, description, currencyId, amount, userId, isExternal, lastTransactionDate, isOverdraft);
        }
    }
}
