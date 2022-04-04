package org.example.mappers;

import org.example.model.TransType;
import org.example.model.Transaction;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionDtoMapperTest {

    @Test
    public void should_createTransactionDto() {
        var transaction = new Transaction(1, "123", 7, TransType.DEBIT, new BigDecimal("100.00"));
        var transactionDto = TransactionDtoMapper.createTransactionDto(transaction);

        assertEquals("123", transactionDto.getTransId());
        assertEquals(7, transactionDto.getPlayerId());
        assertEquals(TransType.DEBIT, transactionDto.getType());
        assertEquals(new BigDecimal("100.00"), transactionDto.getAmount());
    }

}