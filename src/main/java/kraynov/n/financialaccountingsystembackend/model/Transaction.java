package kraynov.n.financialaccountingsystembackend.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public interface Transaction {
    String getId();

    String getDescription();

    String getSenderNodeId();

    String getReceiverNodeId();

    BigDecimal getSenderAmount();

    BigDecimal getReceiverAmount();

    Timestamp getTime();
}
