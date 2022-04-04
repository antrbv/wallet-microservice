package org.example.mappers;

import lombok.experimental.UtilityClass;
import org.example.dto.TransactionDto;
import org.example.model.Transaction;

@UtilityClass
public class TransactionDtoMapper {

    public static TransactionDto createTransactionDto(Transaction transaction) {
        return new TransactionDto(transaction.getTransId(),
                transaction.getPlayerId(),
                transaction.getType(),
                transaction.getAmount()
        );
    }
}
