package kraynov.n.financialaccountingsystembackend.to;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record NodeRequestTo(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("currencyId") String currencyId,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("userId") String userId,
        @JsonProperty("external") boolean isExternal,
        @JsonProperty("overdraft") boolean isOverdraft,
        @JsonProperty("archived") boolean isArchived
) {
}