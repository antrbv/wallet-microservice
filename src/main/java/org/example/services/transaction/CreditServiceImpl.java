package org.example.services.transaction;

import org.example.model.TransType;
import org.example.model.Transaction;
import org.example.services.player.PlayerService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

@Service
public class CreditServiceImpl extends AbstractOperationService implements CreditService {

    public CreditServiceImpl(PlayerService playerService, DuplicateService duplicateService, TransactionService transactionService) {
        super(playerService, duplicateService, transactionService);
    }

    @Override
    protected TransType getTransType() {
        return TransType.CREDIT;
    }

    @Override
    protected boolean checkTransactionAmount(BigDecimal balance, BigDecimal amount) {
        return amount.compareTo(ZERO) != 0;
    }

    @Override
    protected void balanceAction(Integer clientId, BigDecimal amount) {
        playerService.increaseBalance(clientId, amount);
    }

    @Override
    public Transaction credit(String transId, Integer playerId, BigDecimal amount) {
        return process(transId, playerId, amount);
    }
}
