package kraynov.n.financialaccountingsystembackend.model.impl;

import kraynov.n.financialaccountingsystembackend.model.Node;

import java.math.BigDecimal;

public class SimpleNodeImpl implements Node {
    private final int id;
    private final String name;
    private final String description;
    private final int currencyId;
    private final BigDecimal amount;
    private final boolean isExternal;

    public SimpleNodeImpl(int id, String name, String description, int currencyId, BigDecimal amount, boolean isExternal) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currencyId = currencyId;
        this.amount = amount;
        this.isExternal = isExternal;
    }

    @Override
    public int getId() {
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
    public boolean isExternal() {
        return isExternal;
    }
}
