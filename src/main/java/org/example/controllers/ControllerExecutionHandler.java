package org.example.controllers;

import org.example.exception.PlayerNotFoundException;
import org.example.exception.TransactionBadAmountException;
import org.example.exception.TransactionDuplicateException;
import org.example.exception.WalletMicroserviceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExecutionHandler {

    @ExceptionHandler(value = PlayerNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void playerNotFoundException(PlayerNotFoundException ex) {
    }

    @ExceptionHandler(value = TransactionBadAmountException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public void transactionBadAmountException(TransactionBadAmountException ex) {
    }

    @ExceptionHandler(value = TransactionDuplicateException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public void transactionDuplicateException(TransactionDuplicateException ex) {
    }

    @ExceptionHandler(value = WalletMicroserviceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public void walletMicroserviceException(WalletMicroserviceException ex) {
    }

}