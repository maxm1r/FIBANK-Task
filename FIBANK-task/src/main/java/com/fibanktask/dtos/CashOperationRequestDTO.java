package com.fibanktask.dtos;

import com.fibanktask.enums.CurrencyType;
import com.fibanktask.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CashOperationRequestDTO {

        private TransactionType transactionType;
        private CurrencyType currencyType;
        private int amountToDeposit;
        private List<Denomination> denominations;
}
