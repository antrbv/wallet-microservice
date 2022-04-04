package org.example.services.transaction;

import org.example.exception.PlayerNotFoundException;
import org.example.exception.TransactionBadAmountException;
import org.example.exception.TransactionDuplicateException;
import org.example.model.TransType;
import org.example.model.Transaction;
import org.example.services.player.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class CreditServiceImplTest {

    private CreditService creditService;

    private final PlayerService playerService = Mockito.mock(PlayerService.class);
    private final DuplicateService duplicateService = Mockito.mock(DuplicateService.class);
    private final TransactionService transactionService = Mockito.mock(TransactionService.class);

    @BeforeEach
    public void init() {
        creditService = new CreditServiceImpl(playerService, duplicateService, transactionService);
    }

    @Test
    public void should_updateBalance_andAddTrans() {
        Mockito.when(playerService.existById(any())).thenReturn(true);
        Mockito.when(playerService.getBalance(any())).thenReturn(new BigDecimal("100.00"));

        Mockito.when(duplicateService.hasDuplicate(any())).thenReturn(false);

        creditService.credit("111", 0, new BigDecimal("30.00"));

        var amountCaptor = ArgumentCaptor.forClass(BigDecimal.class);
        var transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        Mockito.verify(playerService).increaseBalance(any(), amountCaptor.capture());
        Mockito.verify(transactionService).saveTransaction(transactionCaptor.capture());

        assertEquals(new BigDecimal("30.00"), amountCaptor.getValue());
        assertEquals("111", transactionCaptor.getValue().getTransId());
        assertEquals(new BigDecimal("30.00"), transactionCaptor.getValue().getAmount());
        assertEquals(TransType.CREDIT, transactionCaptor.getValue().getType());
    }

    @Test
    public void should_trowException_dontUpdateBalance_andDontAddTrans_ifPlayerAbsent() {
        Mockito.when(playerService.existById(any())).thenReturn(false);
        Mockito.when(playerService.getBalance(any())).thenReturn(null);

        Mockito.when(duplicateService.hasDuplicate(any())).thenReturn(false);

        assertThrows(PlayerNotFoundException.class, () ->
                creditService.credit("111", 0, new BigDecimal("30.00")));

        var amountCaptor = ArgumentCaptor.forClass(BigDecimal.class);
        var transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        Mockito.verify(playerService, times(0)).increaseBalance(any(), amountCaptor.capture());
        Mockito.verify(transactionService, times(0)).saveTransaction(transactionCaptor.capture());
    }

    @Test
    public void should_throwException_dontUpdateBalance_andDontAddTrans_ifDuplicate() {
        Mockito.when(playerService.existById(any())).thenReturn(true);
        Mockito.when(playerService.getBalance(any())).thenReturn(new BigDecimal("100.00"));

        Mockito.when(duplicateService.hasDuplicate(any())).thenReturn(true);

        assertThrows(TransactionDuplicateException.class, () ->
                creditService.credit("111", 0, new BigDecimal("30.00")));

        var amountCaptor = ArgumentCaptor.forClass(BigDecimal.class);
        var transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        Mockito.verify(playerService, times(0)).increaseBalance(any(), amountCaptor.capture());
        Mockito.verify(transactionService, times(0)).saveTransaction(transactionCaptor.capture());
    }

    @Test
    public void should_throwException_dontUpdateBalance_andDontAddTrans_ifAmountIsZero() {
        Mockito.when(playerService.existById(any())).thenReturn(true);
        Mockito.when(playerService.getBalance(any())).thenReturn(new BigDecimal("10.00"));

        Mockito.when(duplicateService.hasDuplicate(any())).thenReturn(false);

        assertThrows(TransactionBadAmountException.class, () ->
                creditService.credit("111", 0, new BigDecimal("0.00")));

        var amountCaptor = ArgumentCaptor.forClass(BigDecimal.class);
        var transactionCaptor = ArgumentCaptor.forClass(Transaction.class);
        Mockito.verify(playerService, times(0)).increaseBalance(any(), amountCaptor.capture());
        Mockito.verify(transactionService, times(0)).saveTransaction(transactionCaptor.capture());
    }

}