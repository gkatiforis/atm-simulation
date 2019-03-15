package com.katiforis.assignment;

import com.katiforis.assignment.atm.ATM;
import com.katiforis.assignment.atm.components.Console;
import com.katiforis.assignment.atm.components.MoneyDispenser;
import com.katiforis.assignment.integration.BankService;

import java.util.HashMap;
import java.util.Map;

public class ATMSimulation {
    public static void main(String[] args){
        Map<Integer, Integer> notes = new HashMap<>();
        notes.put(MoneyDispenser.NOTE_50, 5);
        notes.put(MoneyDispenser.NOTE_20, 3);
        ATM atm = new ATM(new Console(), new MoneyDispenser(notes), new BankService());
        atm.startUp();
        atm.startNewSession();
    }
}
