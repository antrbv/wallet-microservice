package org.example.services.transaction;

import org.example.model.Transaction;

import java.math.BigDecimal;

public interface DebitService {
    Transaction debit(String transId, Integer playerId, BigDecimal amount);
}
