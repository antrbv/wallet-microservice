package org.example.services.transaction;

import org.example.model.Transaction;

import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactions(Integer playerId);

    boolean existByTransId(String transId);

    Transaction saveTransaction(Transaction transaction);
}
