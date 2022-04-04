package org.example.services.transaction;

import org.example.model.Transaction;

import java.math.BigDecimal;

public interface CreditService {
    Transaction credit(String transId, Integer playerId, BigDecimal amount);
}
