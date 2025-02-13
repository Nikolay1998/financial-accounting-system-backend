package kraynov.n.financialaccountingsystembackend.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Node {
    String getId();

    String getName();

    String getDescription();

    String getCurrencyId();

    BigDecimal getAmount();

    String getUserId();

    boolean isExternal();

    LocalDate getLastTransactionDate();

    boolean isOverdraft();

    boolean isArchived();
}
