package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.PlayerDto;
import org.example.dto.TransactionDto;
import org.example.mappers.TransactionDtoMapper;
import org.example.services.player.PlayerService;
import org.example.services.transaction.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PlayerRestController {

    private final PlayerService playerService;
    private final TransactionService transactionService;

    @GetMapping(value = "/balance/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public PlayerDto balance(@PathVariable(name = "id") int id) {
        var balance = playerService.getBalance(id);
        return new PlayerDto(id, balance);
    }

    @GetMapping(value = "/history/{id}", produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public List<TransactionDto> transactionsHistory(@PathVariable(name = "id") int id) {
        var transactions = transactionService.getTransactions(id);
        return transactions.stream()
                .map(TransactionDtoMapper::createTransactionDto)
                .collect(Collectors.toList());
    }

}
