package com.fibanktask.controllers;

import com.fibanktask.dtos.CashOperationRequestDTO;
import com.fibanktask.dtos.CashOperationResponseDTO;
import com.fibanktask.dtos.CurrencyBalance;
import com.fibanktask.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/cash-operation")
    public CashOperationResponseDTO cashOperation(@RequestBody CashOperationRequestDTO request){
        return transactionService.cashOperation(request);
    }

    @GetMapping("/cash-balance")
    public List<CurrencyBalance> getBalance(){
        return transactionService.getBalances();
    }

}
