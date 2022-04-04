package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class PlayerDto {

    private Integer id;
    private BigDecimal balance;

}
