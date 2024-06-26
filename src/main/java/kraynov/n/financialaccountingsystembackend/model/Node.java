package kraynov.n.financialaccountingsystembackend.model;

import java.math.BigDecimal;

public interface Node {
    String getId();

    String getName();

    String getDescription();

    String getCurrencyId();

    BigDecimal getAmount();

    String getUserId();

    boolean isExternal();
}
