package org.example.services.player;

import java.math.BigDecimal;

public interface PlayerService {
    boolean existById(Integer id);

    BigDecimal getBalance(Integer id);

    void increaseBalance(Integer id, BigDecimal amount);

    void reduceBalance(Integer id, BigDecimal amount);

}
