package kraynov.n.financialaccountingsystembackend.model;

import java.math.BigDecimal;

public interface Node {
    String getId();

    String getName();

    String getDescription();

    int getCurrencyId();

    BigDecimal getAmount();

    boolean isExternal();
}
