package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.TransType;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionDto {

    private String transId;
    private Integer playerId;
    private TransType type;
    private BigDecimal amount;


}
