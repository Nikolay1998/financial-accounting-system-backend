package kraynov.n.financialaccountingsystembackend.dto;

import lombok.Builder;

@Builder
public record CurrencyDto(
        String id,
        String shortName,
        String fullName,
        String symbol,
        short isoCode
) {
}
