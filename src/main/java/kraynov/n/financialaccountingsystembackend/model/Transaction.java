package kraynov.n.financialaccountingsystembackend.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface Transaction {
    String getId();

    String getDescription();

    String getSenderNodeId();

    String getReceiverNodeId();

    BigDecimal getSenderAmount();

    BigDecimal getReceiverAmount();

    LocalDate getDateTime(); //toDo: change to Date

    boolean isCancelled();

    String getUserId();

    boolean isFromExternal();

    boolean isToExternal();

    String getSenderCurrencyId();

    String getReceiverCurrencyId();

    String getSenderName();

    String getReceiverName();
}
