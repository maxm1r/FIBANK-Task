package com.fibanktask.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CurrencyBalance {

    private int balance;
    private List<Denomination> denominations = new ArrayList<>();

    public void addDenomination(Denomination denomination) {
        this.denominations.add(denomination);
    }

    public CurrencyBalance(int startingAmount, int denomination1Value, int denomination1Quantity, int denomination2Value, int denomination2Quantity) {
        this.balance = startingAmount;
        this.denominations.add(new Denomination(denomination1Value, denomination1Quantity));
        this.denominations.add(new Denomination(denomination2Value, denomination2Quantity));
    }
}
