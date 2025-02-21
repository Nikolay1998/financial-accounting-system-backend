package kraynov.n.financialaccountingsystembackend.dto;

import lombok.Builder;
import lombok.Data;
import lombok.With;

import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@With
public class NodeDto {
    private final String id;
    private final String name;
    private final String description;
    private final String currencyId;
    private final BigDecimal amount;
    private final String userId;
    private final boolean isExternal;
    private final boolean isOverdraft;
    private final boolean isArchived;

    private NodeDto(String id, String name, String description, String currencyId, BigDecimal amount, String userId, boolean isExternal, boolean isOverdraft, boolean isArchived) {
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
