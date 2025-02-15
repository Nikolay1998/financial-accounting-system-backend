package kraynov.n.financialaccountingsystembackend.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class NodeRequestTO {
    @JsonProperty("id")
    private final String id;
    @JsonProperty("name")
    private final String name;
    @JsonProperty("description")
    private final String description;
    @JsonProperty("currencyId")
    private final String currencyId;
    @JsonProperty("amount")
    private final BigDecimal amount;
    @JsonProperty("userId")
    private final String userId;
    @JsonProperty("external")
    private final boolean isExternal;
    @JsonProperty("overdraft")
    private final boolean isOverdraft;
    @JsonProperty("archived")
    private final boolean isArchived;

    private NodeRequestTO(String id, String name, String description, String currencyId, BigDecimal amount, String userId, boolean isExternal, boolean isOverdraft, boolean isArchived) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.currencyId = currencyId;
        this.amount = amount;
        this.userId = userId;
        this.isExternal = isExternal;
        this.isOverdraft = isOverdraft;
        this.isArchived = isArchived;
    }
}