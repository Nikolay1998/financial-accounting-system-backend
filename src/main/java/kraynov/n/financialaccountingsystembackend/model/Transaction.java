package kraynov.n.financialaccountingsystembackend.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface Transaction {
    int getId();

    String getDescription();

    int getSenderNodeId();

    int getReceiverNodeId();

    BigDecimal getSenderAmount();

    BigDecimal getReceiverAmount();

    Timestamp getTime();
}
