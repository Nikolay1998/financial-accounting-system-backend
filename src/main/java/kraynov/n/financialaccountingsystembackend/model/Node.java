package kraynov.n.financialaccountingsystembackend.model;

import java.math.BigDecimal;

public interface Node {
    int getId();

    String getName();

    String getDescription();

    int getCurrencyId();

    BigDecimal getAmount();

    boolean isExternal();
}
