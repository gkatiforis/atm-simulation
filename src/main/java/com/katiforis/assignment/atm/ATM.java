package com.katiforis.assignment.atm;

import com.katiforis.assignment.atm.components.Console;
import com.katiforis.assignment.atm.components.MoneyDispenser;
import com.katiforis.assignment.exception.TransactionException;
import com.katiforis.assignment.integration.BankService;
import com.katiforis.assignment.integration.Status;

import java.util.Map;

/**
 * Represents a ATM machine.
 * Responsible to interact with user and dispense cash.
 */
public class ATM {

    private Console console;
    private MoneyDispenser moneyDispenser;
    private BankService bankService;

    public ATM(Console console, MoneyDispenser moneyDispenser, BankService bankService){
        this.console = console;
        this.moneyDispenser = moneyDispenser;
        this.bankService = bankService;
    }

    /**
     * Initialize ATM
     */
    public void startUp(){
        console.printWelcomeMessage();
        console.printAvailableNotes(moneyDispenser.getAvailableNotes());
    }

    /**
     * Starts new session with the customer
     */
    public void startNewSession(){

            Integer moneyToWithdraw = console.askForAmountToWithdraw();
            if(!moneyDispenser.sufficientMoneyAvailable(moneyToWithdraw)) {
                console.printErrorMessage("Insufficient cash available.");

            }
            else{
                try {
                    Status status = bankService.sendRequest();
                    if(status.getCode() == Status.SUCCESS){
                        Map<Integer, Integer> money = moneyDispenser.dispenseMoney(moneyToWithdraw);
                        console.printWithdrawNotes(money);
                    }else if(status.getCode() == Status.INSUFFICIENT_BALANCE){
                        console.printErrorMessage("Insufficient account balance.");
                    }else{
                        console.printErrorMessage("Error: " + status.getMessage());
                    }
                }
                catch (IllegalStateException e) {
                    console.printErrorMessage("Error during fetching money from machine");
                }
                catch (TransactionException e) {
                    console.printErrorMessage("Error during sending message to the bank. Try again later.");
                }
            }

        console.printAvailableNotes(moneyDispenser.getAvailableNotes());

        if(console.askForNewTransaction()){
            startNewSession();
        }
    }

    public Console getConsole() {
        return console;
    }

    public MoneyDispenser getMoneyDispenser() {
        return moneyDispenser;
    }

    public BankService getBankService() {
        return bankService;
    }
}
