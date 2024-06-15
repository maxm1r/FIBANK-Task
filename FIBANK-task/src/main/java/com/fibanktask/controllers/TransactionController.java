package com.fibanktask.controllers;

import com.fibanktask.dtos.CashOperationRequestDTO;
import com.fibanktask.dtos.CashOperationResponseDTO;
import com.fibanktask.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/cash-operation")
    public CashOperationResponseDTO depositBgn(@RequestBody CashOperationRequestDTO request){
        return transactionService.cashOperation(request);
    }

}
