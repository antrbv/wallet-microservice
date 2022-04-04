package org.example.services.transaction;

import org.example.model.TransType;
import org.example.model.Transaction;
import org.example.services.player.PlayerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

@Service
public class DebitServiceImpl extends AbstractOperationService implements DebitService {

    public DebitServiceImpl(PlayerService playerService, DuplicateService duplicateService, TransactionService transactionService) {
        super(playerService, duplicateService, transactionService);
    }

    @Override
    protected TransType getTransType() {
        return TransType.DEBIT;
    }

    @Override
    protected boolean checkTransactionAmount(BigDecimal balance, BigDecimal amount) {
        return amount.compareTo(ZERO) != 0 && balance.compareTo(amount) >= 0;
    }

    @Override
    protected void balanceAction(Integer clientId, BigDecimal amount) {
        playerService.reduceBalance(clientId, amount);
    }

    @Override
    public Transaction debit(String transId, Integer playerId, BigDecimal amount) {
        return process(transId, playerId, amount);
    }
}
