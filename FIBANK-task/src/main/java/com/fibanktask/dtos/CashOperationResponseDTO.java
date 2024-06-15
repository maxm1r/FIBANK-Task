package com.fibanktask.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CashOperationResponseDTO {

    private String status;
    private String message;
    private double depositedAmount;
    private LocalDateTime timestamp;
}
