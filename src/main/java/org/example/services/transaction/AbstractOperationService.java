package org.example.services.transaction;

import lombok.RequiredArgsConstructor;
import org.example.exception.PlayerNotFoundException;
import org.example.exception.TransactionBadAmountException;
import org.example.exception.TransactionDuplicateException;
import org.example.model.TransType;
import org.example.model.Transaction;
import org.example.services.player.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RequiredArgsConstructor
public abstract class AbstractOperationService {

    protected final Logger log = LoggerFactory.getLogger(getClass().getName());

    protected final PlayerService playerService;
    private final DuplicateService duplicateService;
    private final TransactionService transactionService;

    protected abstract TransType getTransType();

    protected abstract boolean checkTransactionAmount(BigDecimal balance, BigDecimal amount);

    protected abstract void balanceAction(Integer clientId, BigDecimal amount);

    @Transactional
    public Transaction process(String transId, Integer playerId, BigDecimal amount) {
        if (!playerService.existById(playerId)) {
            log.debug("Player not found: {}", playerId);
            throw new PlayerNotFoundException();
        }

        if (duplicateService.hasDuplicate(transId)) {
            log.debug("Duplicate transaction: {}", transId);
            throw new TransactionDuplicateException();
        }

        var balance = playerService.getBalance(playerId);
        if (!checkTransactionAmount(balance, amount)) {
            log.debug("Transaction amount is invalid: {}. Balance: {}", amount, balance);
            throw new TransactionBadAmountException();
        }

        balanceAction(playerId, amount);
        var transaction = transactionService.saveTransaction(
                new Transaction(null, transId, playerId, getTransType(), amount));

        log.debug("Transaction saved: {}", transaction);
        return transaction;
    }

}
