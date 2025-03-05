package kraynov.n.financialaccountingsystembackend.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class NodeResponseTO {
    @JsonProperty("id")
    private final String id;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("description")
    private final String description;
    @JsonProperty("currencySymbol")
    private final String currencySymbol;
    @JsonProperty("currencyId")
    private final String currencyId;
    @JsonProperty("amount")
    private final BigDecimal amount;
    @JsonProperty("userId")
    private final String userId;
    @JsonProperty("external")
    private final boolean isExternal;
    @JsonProperty("lastTransactionDate")
    private final LocalDate lastTransactionDate;
    @JsonProperty("overdraft")
    private final boolean isOverdraft;
    @JsonProperty("archived")
    private final boolean isArchived;


}
