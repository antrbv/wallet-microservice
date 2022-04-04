package org.example.services.transaction;

import lombok.RequiredArgsConstructor;
import org.example.exception.PlayerNotFoundException;
import org.example.exception.TransactionDuplicateException;
import org.example.model.Transaction;
import org.example.repostory.TransactionRepository;
import org.example.services.player.PlayerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BaseTransactionService implements TransactionService {

    private final PlayerService playerService;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public List<Transaction> getTransactions(Integer playerId) {
        if (!playerService.existById(playerId)) {
            throw new PlayerNotFoundException();
        }
        return transactionRepository.findAllByPlayerId(playerId);
    }

    @Override
    public boolean existByTransId(String transId) {
        return transactionRepository.existsTransactionByTransId(transId);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        try {
            return transactionRepository.save(transaction);
        } catch (Exception e) {
            throw new TransactionDuplicateException();
        }
    }
}
