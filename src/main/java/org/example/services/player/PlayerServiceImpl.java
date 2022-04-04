package org.example.services.player;

import lombok.RequiredArgsConstructor;
import org.example.exception.PlayerNotFoundException;
import org.example.exception.TransactionBadAmountException;
import org.example.model.Player;
import org.example.repostory.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    public boolean existById(Integer id) {
        return playerRepository.existsById(id);
    }

    @Override
    public BigDecimal getBalance(Integer id) {
        return getPlayer(id).getBalance();
    }

    @Override
    public void increaseBalance(Integer id, BigDecimal amount) {
        try {
            playerRepository.increaseBalance(id, amount);
        } catch (Exception e) {
            throw new TransactionBadAmountException();
        }
    }

    @Override
    @Transactional
    public void reduceBalance(Integer id, BigDecimal amount) {
        try {
            playerRepository.reduceBalance(id, amount);
        } catch (Exception e) {
            throw new TransactionBadAmountException();
        }
    }

    private Player getPlayer(Integer id) {
        return playerRepository.findById(id).orElseThrow(PlayerNotFoundException::new);
    }

}
