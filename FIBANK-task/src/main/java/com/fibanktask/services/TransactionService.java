package com.fibanktask.services;

import com.fibanktask.dtos.CashOperationRequestDTO;
import com.fibanktask.dtos.CashOperationResponseDTO;
import com.fibanktask.dtos.CurrencyBalance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionService {

    CashOperationResponseDTO cashOperation(CashOperationRequestDTO request);

    List<CurrencyBalance> getBalances();
}
