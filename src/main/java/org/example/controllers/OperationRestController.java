package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.OperationRequest;
import org.example.dto.TransactionDto;
import org.example.mappers.TransactionDtoMapper;
import org.example.services.transaction.CreditService;
import org.example.services.transaction.DebitService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OperationRestController {

    private final DebitService debitService;
    private final CreditService creditService;

    @PostMapping("/debit")
    @ResponseStatus(value = HttpStatus.OK)
    public TransactionDto debit(@RequestBody OperationRequest operationRequest) {
        var transaction = debitService.debit(
                operationRequest.getTransId(),
                operationRequest.getPlayerId(),
                operationRequest.getAmount()
        );

        return TransactionDtoMapper.createTransactionDto(transaction);
    }

    @PostMapping("/credit")
    @ResponseStatus(value = HttpStatus.OK)
    public TransactionDto credit(@RequestBody OperationRequest operationRequest) {
        var transaction = creditService.credit(
                operationRequest.getTransId(),
                operationRequest.getPlayerId(),
                operationRequest.getAmount()
        );

        return TransactionDtoMapper.createTransactionDto(transaction);
    }

}
