package kraynov.n.financialaccountingsystembackend.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CurrencyDTO {
    private final String id;
    private final String shortName;
    private final String fullName;
    private final String symbol;
    private final short isoCode;

    private CurrencyDTO(String id, String shortName, String fullName, String symbol, short isoCode) {
        this.id = id;
        this.shortName = shortName;
        this.fullName = fullName;
        this.symbol = symbol;
        this.isoCode = isoCode;
    }
}
