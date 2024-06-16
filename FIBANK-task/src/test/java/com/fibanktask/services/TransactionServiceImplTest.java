package com.fibanktask.services;

import com.fibanktask.dtos.CashOperationRequestDTO;
import com.fibanktask.dtos.Denomination;
import com.fibanktask.enums.CurrencyType;
import com.fibanktask.enums.TransactionType;
import com.fibanktask.exceptions.BadRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;


    @Test
    public void testCashOperation_InvalidTransactionRequest() {
        CashOperationRequestDTO request = new CashOperationRequestDTO();
        request.setTransactionType(null);
        request.setCurrencyType(CurrencyType.BGN);
        request.setAmountToDeposit(600);
        Denomination denomination1 = new Denomination(10,10);
        Denomination denomination2 = new Denomination(10,50);
        List<Denomination> list = new ArrayList<>();
        list.add(denomination1);
        list.add(denomination2);
        request.setDenominations(list);

        Exception exception = assertThrows(BadRequestException.class, () -> {
            transactionService.cashOperation(request);
        });

        assertEquals("Invalid value/s in the request!", exception.getMessage());
    }

    @Test
    public void testCashOperation_InvalidCurrencyRequest() {
        CashOperationRequestDTO request = new CashOperationRequestDTO();
        request.setTransactionType(TransactionType.DEPOSIT);
        request.setCurrencyType(null);
        request.setAmountToDeposit(600);
        Denomination denomination1 = new Denomination(10,10);
        Denomination denomination2 = new Denomination(10,50);
        List<Denomination> list = new ArrayList<>();
        list.add(denomination1);
        list.add(denomination2);
        request.setDenominations(list);

        Exception exception = assertThrows(BadRequestException.class, () -> {
            transactionService.cashOperation(request);
        });

        assertEquals("Invalid value/s in the request!", exception.getMessage());
    }

    @Test
    public void testCashOperation_InvalidDenominationRequest() {
        CashOperationRequestDTO request = new CashOperationRequestDTO();
        request.setTransactionType(TransactionType.DEPOSIT);
        request.setCurrencyType(CurrencyType.BGN);
        request.setAmountToDeposit(600);
        Denomination denomination1 = new Denomination(20,10);
        Denomination denomination2 = new Denomination(10,50);
        List<Denomination> list = new ArrayList<>();
        list.add(denomination1);
        list.add(denomination2);
        request.setDenominations(list);

        Exception exception = assertThrows(BadRequestException.class, () -> {
            transactionService.cashOperation(request);
        });

        assertEquals("Invalid value/s in the request!", exception.getMessage());
    }
}