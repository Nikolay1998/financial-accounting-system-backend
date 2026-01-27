package kraynov.n.financialaccountingsystembackend.to;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RateTo {
    private String fromCurrencyId;
    private String toCurrencyId;
    //    private LocalDate validFrom;
//    private LocalDate validTo;
    private BigDecimal rate;
}
