package com.fibanktask.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Denomination {

    private int quantity;
    private int value;

    @Override
    public String toString() {
        return " " + quantity +
                " x " + value + " ";
    }
}
