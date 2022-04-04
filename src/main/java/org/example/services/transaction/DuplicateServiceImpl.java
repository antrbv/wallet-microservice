package org.example.services.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DuplicateServiceImpl implements DuplicateService {

    private final TransactionService baseTransactionService;

    @Override
    public boolean hasDuplicate(String transId) {
        return baseTransactionService.existByTransId(transId);
    }
}
