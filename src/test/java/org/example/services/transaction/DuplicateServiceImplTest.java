package org.example.services.transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

class DuplicateServiceImplTest {

    private DuplicateService duplicateService;

    private final TransactionService transactionService = Mockito.mock(TransactionService.class);

    @BeforeEach
    public void init() {
        duplicateService = new DuplicateServiceImpl(transactionService);
    }

    @Test
    public void should_returnFalse_ifTransactionAbsent() {
        Mockito.when(transactionService.existByTransId(any()))
                .thenReturn(false);

        assertFalse(duplicateService.hasDuplicate("111"));
    }


    @Test
    public void should_returnTrue_ifTransactionPresent() {
        Mockito.when(transactionService.existByTransId(any()))
                .thenReturn(true);

        assertTrue(duplicateService.hasDuplicate("222"));
    }

}