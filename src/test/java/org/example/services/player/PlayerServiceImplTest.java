package org.example.services.player;

import org.example.exception.PlayerNotFoundException;
import org.example.exception.TransactionBadAmountException;
import org.example.model.Player;
import org.example.repostory.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlayerServiceImplTest {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private PlayerRepository playerRepository;

    @BeforeEach
    public void init() {
        playerRepository.deleteAll();
    }

    @Test
    public void should_getBalance() {
        var id = playerRepository.save(new Player(null, new BigDecimal("100.00"))).getId();
        System.out.println(playerRepository.findAll());
        assertEquals(new BigDecimal("100.00"), playerService.getBalance(id));
    }

    @Test
    public void should_throwException_ifPlayerAbsent() {
        assertThrows(PlayerNotFoundException.class, () ->
                playerService.getBalance(7));
    }

    @Test
    public void should_increaseBalance() {
        var id = playerRepository.save(new Player(null, new BigDecimal("100.00"))).getId();

        playerService.increaseBalance(id, new BigDecimal("50.00"));

        var playerById = playerRepository.findById(id);
        assertTrue(playerById.isPresent());
        assertEquals(new BigDecimal("150.00"), playerById.get().getBalance());
    }

    @Test
    public void should_dontIncreaseBalance_ifPlayerAbsent() {
        playerService.increaseBalance(7, new BigDecimal("200.00"));

        var playerById = playerRepository.findById(7);
        assertTrue(playerById.isEmpty());
    }

    @Test
    public void should_existById_returnTrue_ifPlayerExist() {
        var id = playerRepository.save(new Player(null, new BigDecimal("100.00"))).getId();
        assertTrue(playerService.existById(id));
    }

    @Test
    public void should_existById_returnFalse_ifPlayerAbsent() {
        assertFalse(playerService.existById(7));
    }

    @Test
    public void should_dontUpdate_withNegativeBalance_andThrowException() {
        var id = playerRepository.save(new Player(null, new BigDecimal("100.00"))).getId();

        assertThrows(TransactionBadAmountException.class, () ->
                playerService.reduceBalance(id, new BigDecimal("200.00")));

        var byId = playerRepository.findById(id);
        assertTrue(byId.isPresent());
        assertEquals(new BigDecimal("100.00"), byId.get().getBalance());
    }

}