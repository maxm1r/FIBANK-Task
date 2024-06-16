package com.fibanktask.Utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Constants {

    public static final String API_KEY_HEADER = "FIB-X-AUTH";
    public static final String API_KEY_VALUE = "f9Uie8nNf112hx8s";
    public static final String TRANSACTION_HISTORY_FILE_PATH = "src/main/resources/transaction_history_files/transaction_history.txt";
    public static final String TRANSACTION_BALANCES_FILE_PATH = "src/main/resources/transaction_balance_files/transaction_balances.txt";

    public static final int STARTING_BGN_AMOUNT = 1000;

    public static final int STARTING_EUR_AMOUNT = 2000;

    public static final Set<Integer> VALID_DENOMINATION_VALUES = new HashSet<>(Arrays.asList(5, 10, 20, 50, 100));

}
