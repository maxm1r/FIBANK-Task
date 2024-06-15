package com.fibanktask.services;

import com.fibanktask.dtos.CashOperationRequestDTO;
import com.fibanktask.dtos.CashOperationResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface TransactionService {

    CashOperationResponseDTO cashOperation(CashOperationRequestDTO request);
}
