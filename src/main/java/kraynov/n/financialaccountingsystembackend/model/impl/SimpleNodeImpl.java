package kraynov.n.financialaccountingsystembackend.model.impl;

import kraynov.n.financialaccountingsystembackend.model.Node;

import java.math.BigDecimal;

public class SimpleNodeImpl implements Node {
    private final String id;
    private final String name;
    private final String description;
    private final int currencyId;
    private final BigDecimal amount;
    private final String userId;
    private final boolean isExternal;

    private SimpleNodeImpl(String id, String name, String description, int currencyId, BigDecimal amount, String userId, boolean external) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currencyId = currencyId;
        this.amount = amount;
        this.userId = userId;
        this.isExternal = external;
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
    public int getCurrencyId() {
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
    public String toString() {
        return "SimpleNodeImpl{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", currencyId=" + currencyId +
                ", amount=" + amount +
                ", userId='" + userId + '\'' +
                ", isExternal=" + isExternal +
                '}';
    }

    public static class Builder {

        private String id;
        private String name;
        private String description;
        private int currencyId;
        private BigDecimal amount;
        private String userId;
        private boolean isExternal;

        public Builder from(Node node) {
            this.id = node.getId();
            this.name = node.getName();
            this.description = node.getDescription();
            this.currencyId = node.getCurrencyId();
            this.amount = node.getAmount();
            this.userId = node.getUserId();
            this.isExternal = node.isExternal();
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

        public Builder setCurrencyId(int currencyId) {
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

        public Node build() {
            return new SimpleNodeImpl(id, name, description, currencyId, amount, userId, isExternal);
        }
    }
}
