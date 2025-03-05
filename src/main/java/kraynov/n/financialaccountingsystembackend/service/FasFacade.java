package kraynov.n.financialaccountingsystembackend.service;

import kraynov.n.financialaccountingsystembackend.dto.TransactionDto;
import kraynov.n.financialaccountingsystembackend.dto.TransactionExtendedInfoDto;

public interface FasFacade {
    TransactionExtendedInfoDto addTransaction(TransactionDto transaction);

    TransactionExtendedInfoDto cancelTransaction(String transactionId);

    TransactionExtendedInfoDto editTransaction(TransactionDto transaction);

    TransactionExtendedInfoDto restoreTransaction(String transactionId);
}
