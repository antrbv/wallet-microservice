package org.example.services.transaction;

import org.example.exception.PlayerNotFoundException;
import org.example.exception.TransactionDuplicateException;
import org.example.model.Player;
import org.example.model.TransType;
import org.example.model.Transaction;
import org.example.repostory.PlayerRepository;
import org.example.repostory.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BaseTransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void init() {
        playerRepository.deleteAll();
        transactionRepository.deleteAll();
    }

    @Test
    public void should_returnTransactionsList() {
        var id = playerRepository.save(new Player(null, new BigDecimal("100.00"))).getId();

        var trans1 = new Transaction(null, "111", id, TransType.DEBIT, new BigDecimal("10.00"));
        var trans2 = new Transaction(null, "222", id, TransType.DEBIT, new BigDecimal("10.00"));
        var otherClientTrans = new Transaction(null, "000", 0, TransType.DEBIT, new BigDecimal("10.00"));
        var trans3 = new Transaction(null, "333", id, TransType.DEBIT, new BigDecimal("10.00"));

        transactionRepository.save(trans1);
        transactionRepository.save(trans2);
        transactionRepository.save(otherClientTrans);
        transactionRepository.save(trans3);

        assertThat(transactionService.getTransactions(id))
                .isNotNull()
                .hasSize(3)
                .map(Transaction::getTransId)
                .containsExactly("111", "222", "333");
    }

    @Test
    public void should_getTransactions_returnEmptyList_ifPlayerHasNotIt() {
        var id = playerRepository.save(new Player(null, new BigDecimal("100.00"))).getId();

        var trans1 = new Transaction(null, "111", 0, TransType.DEBIT, new BigDecimal("10.00"));
        var trans2 = new Transaction(null, "222", 0, TransType.DEBIT, new BigDecimal("10.00"));
        var trans3 = new Transaction(null, "333", 0, TransType.DEBIT, new BigDecimal("10.00"));

        transactionRepository.save(trans1);
        transactionRepository.save(trans2);
        transactionRepository.save(trans3);

        assertThat(transactionService.getTransactions(id))
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void should_getTransactions_throwException_ifPlayerAbsent() {
        var id = playerRepository.save(new Player(null, new BigDecimal("100.00"))).getId();

        var trans1 = new Transaction(null, "111", id + 1, TransType.DEBIT, new BigDecimal("10.00"));
        var trans2 = new Transaction(null, "222", id + 1, TransType.DEBIT, new BigDecimal("10.00"));
        var trans3 = new Transaction(null, "333", id + 1, TransType.DEBIT, new BigDecimal("10.00"));

        transactionRepository.save(trans1);
        transactionRepository.save(trans2);
        transactionRepository.save(trans3);

        assertThrows(PlayerNotFoundException.class, () ->
                transactionService.getTransactions(id + 1));
    }

    @Test
    public void should_check_existByTransId() {
        var id = playerRepository.save(new Player(null, new BigDecimal("100.00"))).getId();

        transactionRepository.save(new Transaction(null, "111", id, TransType.DEBIT, new BigDecimal("10.00")));
        transactionRepository.save(new Transaction(null, "222", id, TransType.DEBIT, new BigDecimal("10.00")));
        transactionRepository.save(new Transaction(null, "333", id, TransType.DEBIT, new BigDecimal("10.00")));

        assertTrue(transactionService.existByTransId("222"));
        assertFalse(transactionService.existByTransId("444"));
    }

    @Test
    public void should_throwException_ifSaveDuplicateTrans() {
        var id = playerRepository.save(new Player(null, new BigDecimal("100.00"))).getId();

        transactionRepository.save(new Transaction(null, "111", id, TransType.DEBIT, new BigDecimal("10.00")));
        transactionRepository.save(new Transaction(null, "222", id, TransType.DEBIT, new BigDecimal("10.00")));
        transactionRepository.save(new Transaction(null, "333", id, TransType.DEBIT, new BigDecimal("10.00")));

        assertThrows(TransactionDuplicateException.class, () ->
                transactionService.saveTransaction(new Transaction(null, "222", id, TransType.DEBIT, new BigDecimal("10.00"))));
    }

}