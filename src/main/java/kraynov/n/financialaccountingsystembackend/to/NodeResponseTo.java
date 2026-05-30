package kraynov.n.financialaccountingsystembackend.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record NodeResponseTo(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("description") String description,
        @JsonProperty("currencySymbol") String currencySymbol,
        @JsonProperty("currencyId") String currencyId,
        @JsonProperty("amount") BigDecimal amount,
        @JsonProperty("userId") String userId,
        @JsonProperty("external") boolean isExternal,
        @JsonProperty("lastTransactionDate") LocalDate lastTransactionDate,
        @JsonProperty("overdraft") boolean isOverdraft,
        @JsonProperty("archived") boolean isArchived
) {
}
