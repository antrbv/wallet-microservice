package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OperationRequest {

    private String transId;
    private Integer playerId;
    private BigDecimal amount;
}
