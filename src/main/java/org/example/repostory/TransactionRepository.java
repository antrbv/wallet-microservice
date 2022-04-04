package org.example.repostory;

import org.example.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    List<Transaction> findAllByPlayerId(Integer playerId);

    boolean existsTransactionByTransId(String transId);

}
