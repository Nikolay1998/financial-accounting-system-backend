package kraynov.n.financialaccountingsystembackend.to;

import lombok.Data;

@Data
public class CurrencyIdPairTo {
    private final String fromId;
    private final String toId;
}
