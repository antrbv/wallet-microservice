package org.example.repostory;

import org.example.model.Player;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {

    @Modifying
    @Query("update player p set p.balance = p.balance + :amount where p.id = :id")
    void increaseBalance(@Param("id") Integer id, @Param("amount") BigDecimal amount);

    @Modifying
    @Query("update player p set p.balance = p.balance - :amount where p.id = :id")
    void reduceBalance(@Param("id") Integer id, @Param("amount") BigDecimal amount);

}
