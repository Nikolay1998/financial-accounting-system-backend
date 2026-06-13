package kraynov.n.financialaccountingsystembackend.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TransactionFilterDto(
        LocalDate from,
        LocalDate to
) {
}
