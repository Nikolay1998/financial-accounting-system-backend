package kraynov.n.financialaccountingsystembackend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class TransactionFilterDto {
    private final LocalDate from;
    private final LocalDate to;
}
