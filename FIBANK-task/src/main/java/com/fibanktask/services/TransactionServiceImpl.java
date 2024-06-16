package com.fibanktask.services;

import com.fibanktask.dtos.*;
import com.fibanktask.enums.CurrencyType;
import com.fibanktask.enums.TransactionType;
import com.fibanktask.exceptions.BadRequestException;
import com.fibanktask.exceptions.FileHandlingException;
import com.fibanktask.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fibanktask.Utils.Constants.*;

@Service
public class TransactionServiceImpl implements  TransactionService{

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private CurrencyBalance balanceBGN = new CurrencyBalance(STARTING_BGN_AMOUNT,50,10,10,50);
    private CurrencyBalance balanceEUR = new CurrencyBalance(STARTING_EUR_AMOUNT,100,10,20,50);

    @Override
    public CashOperationResponseDTO cashOperation(CashOperationRequestDTO request) {

        if(!isValidCashOperationRequest(request)){
            throw new BadRequestException("Invalid value/s in the request!");
        }
        createTxtFilesIfNotExist();

        if (request.getTransactionType()== TransactionType.DEPOSIT){
            deposit(request);
        }else {
            withdraw(request);
        }

        CashOperationResponseDTO response = new CashOperationResponseDTO();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus("Success");
        response.setDepositedAmount(request.getAmountToDeposit());
        if (request.getTransactionType() == TransactionType.DEPOSIT) {
            response.setMessage("Deposited " + response.getDepositedAmount() + " " + request.getCurrencyType());
        }
        else {
            response.setMessage("Withdrew " + response.getDepositedAmount() + " " + request.getCurrencyType());
        }
        return response;
    }

    @Override
    public List<CurrencyBalance> getBalances() {
        List<CurrencyBalance> response = new ArrayList<>();
        response.add(balanceBGN);
        response.add(balanceEUR);

        return response;
    }

    private void deposit(CashOperationRequestDTO request) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        if (request.getCurrencyType() == CurrencyType.BGN) {
            balanceBGN.setBalance(balanceBGN.getBalance() + request.getAmountToDeposit());
        }
        else {
            balanceEUR.setBalance(balanceEUR.getBalance() + request.getAmountToDeposit());
        }

        addDenominations(request.getDenominations(),request.getCurrencyType());

        String transactionBalanceRecord = String.format("Balance: %d %s %s , %d %s %s",
                balanceBGN.getBalance(), CurrencyType.BGN,balanceBGN.getDenominations(),
                balanceEUR.getBalance(), CurrencyType.EUR,balanceEUR.getDenominations());

        String transactionHistoryRecord = String.format("%s Deposit %d %s",
                formattedDateTime,request.getAmountToDeposit() ,request.getCurrencyType());
        writeInFile(TRANSACTION_BALANCES_FILE_PATH,transactionBalanceRecord);
        writeInFile(TRANSACTION_HISTORY_FILE_PATH,transactionHistoryRecord);
    }

    private void withdraw(CashOperationRequestDTO request) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);

        removeDenominations(request.getDenominations(),request.getCurrencyType());

        if (request.getCurrencyType() == CurrencyType.BGN) {
            balanceBGN.setBalance(balanceBGN.getBalance() - request.getAmountToDeposit());
        }
        else {
            balanceEUR.setBalance(balanceEUR.getBalance() - request.getAmountToDeposit());
        }

        String transactionBalanceRecord = String.format("Balance: %d %s %s , %d %s %s",
                balanceBGN.getBalance(), CurrencyType.BGN,balanceBGN.getDenominations(),
                balanceEUR.getBalance(), CurrencyType.EUR,balanceEUR.getDenominations());

        String transactionHistoryRecord = String.format("%s Withdraw %d %s",
                formattedDateTime,request.getAmountToDeposit() ,request.getCurrencyType());
        writeInFile(TRANSACTION_BALANCES_FILE_PATH,transactionBalanceRecord);
        writeInFile(TRANSACTION_HISTORY_FILE_PATH,transactionHistoryRecord);
    }

    private boolean isValidCashOperationRequest(CashOperationRequestDTO request) {

        if (request.getTransactionType() == null ||
                (request.getTransactionType() != TransactionType.DEPOSIT && request.getTransactionType() != TransactionType.WITHDRAW)){
            return false;
        }

        if (request.getCurrencyType() == null ||
                (request.getCurrencyType() != CurrencyType.BGN && request.getCurrencyType() != CurrencyType.EUR)) {
            return false;
        }

        if (request.getAmountToDeposit() <= 0) {
            return false;
        }

        if (request.getDenominations() == null || request.getDenominations().isEmpty()) {
            return false;
        }

        int totalAmount = 0;
        for (Denomination denomination : request.getDenominations()) {
            if (denomination.getValue() <= 0 || denomination.getQuantity() <= 0) {
                return false;
            }
            if (!isValidDenomination(denomination.getValue())){
                return false;
            }
            totalAmount += denomination.getValue() * denomination.getQuantity();
        }

        if (totalAmount != request.getAmountToDeposit()) {
            return false;
        }

        return true;
    }

    private static boolean isValidDenomination(int value) {
        return VALID_DENOMINATION_VALUES.contains(value);
    }

    private void createTxtFilesIfNotExist() {

        File transactionHistory = new File(TRANSACTION_HISTORY_FILE_PATH + "_" + LocalDate.now());

        try {
            if (!transactionHistory.exists()) {
                boolean created = transactionHistory.createNewFile();

                if (created) {
                    logger.info("Successfully created a file at: " + TRANSACTION_HISTORY_FILE_PATH);
                } else {
                    throw new FileHandlingException("Failed to create the file!" );
                }
            }
        } catch (IOException e) {
            throw new FileHandlingException( "An error occurred: " + e.getMessage());
        }

        File transactionDenominations = new File(TRANSACTION_BALANCES_FILE_PATH + "_" + LocalDate.now());
        try {
            if (!transactionDenominations.exists()) {
                boolean created = transactionDenominations.createNewFile();

                if (created) {
                    balanceBGN = new CurrencyBalance(STARTING_BGN_AMOUNT,50,10,10,50);
                    balanceEUR = new CurrencyBalance(STARTING_BGN_AMOUNT,50,10,10,50);
                    logger.info("Successfully created a file at: " + TRANSACTION_BALANCES_FILE_PATH);
                } else {
                    throw new FileHandlingException("Failed to create the file!");
                }
            }
        } catch (IOException e) {
            throw new FileHandlingException("An error occurred: " + e.getMessage());
        }
    }

    private void addDenominations(List<Denomination> denominations, CurrencyType currencyType){
        CurrencyBalance balance;
        if (currencyType== CurrencyType.BGN){
            balance = balanceBGN;
        }
        else {
            balance = balanceEUR;
        }
        for (Denomination newDenomination : denominations) {
            Optional<Denomination> existingDenominationOpt = balance.getDenominations()
                    .stream()
                    .filter(d -> d.getValue() == newDenomination.getValue())
                    .findFirst();

            if (existingDenominationOpt.isPresent()) {
                Denomination existingDenomination = existingDenominationOpt.get();
                existingDenomination.setQuantity(existingDenomination.getQuantity() + newDenomination.getQuantity());
            } else {
                balance.addDenomination(newDenomination);
            }
        }
    }

    private void removeDenominations(List<Denomination> denominations, CurrencyType currencyType) {
        CurrencyBalance balance;
        if (currencyType == CurrencyType.BGN) {
            balance = balanceBGN;
        } else {
            balance = balanceEUR;
        }

        for (Denomination removeDenomination : denominations) {
            Optional<Denomination> existingDenominationOpt = balance.getDenominations()
                    .stream()
                    .filter(d -> d.getValue() == removeDenomination.getValue())
                    .findFirst();

            if (existingDenominationOpt.isPresent()) {
                Denomination existingDenomination = existingDenominationOpt.get();
                if (existingDenomination.getQuantity() >= removeDenomination.getQuantity()) {
                    int newQuantity = existingDenomination.getQuantity() - removeDenomination.getQuantity();
                    if (newQuantity > 0) {
                        existingDenomination.setQuantity(newQuantity);
                    } else {
                        balance.getDenominations().remove(existingDenomination);
                    }
                } else {
                    throw new BadRequestException("Insufficient quantity for denomination: " + removeDenomination.getValue());
                }
            } else {
                throw new NotFoundException("Denomination not found: " + removeDenomination.getValue());
            }
        }
    }

    private void writeInFile(String filePath, String transactionRecord){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath+ "_" + LocalDate.now(), true))) {
            writer.write(transactionRecord);
            writer.newLine();
        } catch (IOException e) {
            throw new FileHandlingException("Error writing in the file!");
        }
    }

}
