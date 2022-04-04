package org.example.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Objects;

@Table("transaction")
@AllArgsConstructor
@Getter
public class Transaction {

    @Id
    @Column("id")
    private final Integer id;

    @Column("trans_id")
    private final String transId;

    @Column("player_id")
    private final Integer playerId;

    @Column("type")
    private final TransType type;

    @Column("amount")
    private final BigDecimal amount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id) && Objects.equals(transId, that.transId) && Objects.equals(playerId, that.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, transId, playerId);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", transId='" + transId + '\'' +
                ", playerId=" + playerId +
                ", type=" + type +
                ", amount=" + amount +
                '}';
    }
}
