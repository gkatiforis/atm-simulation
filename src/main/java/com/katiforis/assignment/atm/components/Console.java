package com.katiforis.assignment.atm.components;

import java.util.Map;
import java.util.Scanner;

/**
 * Represents screen of the ATM.
 * Responsible to interact with user.
 */
public class Console {

    public void printWelcomeMessage() {
        System.out.println("Welcome");
    }

    public void printAvailableNotes(Map<Integer, Integer> notes){
        System.out.println("The available notes are:");
        printNotes(notes);
    }

    public void printWithdrawNotes(Map<Integer, Integer> notes) {
        System.out.println("You have withdraw:");
        printNotes(notes);
    }

    public void printNotes(Map<Integer, Integer> notes){
        notes.entrySet()
                .stream()
                .map(e -> "$" + e.getKey() + ": " + e.getValue())
                .forEach(System.out::println);
    }

    public void printErrorMessage(String message) {
        System.out.println("Error: " + message);
    }


    public Integer askForAmountToWithdraw() {

        boolean isValidAmount;
        do {
            System.out.println("Give the amount of cash to withdraw");
            Scanner scanner = new Scanner(System.in);
            if (isValidAmount = scanner.hasNextInt()) {
                Integer amount = scanner.nextInt();
                if(amount > 0){
                    return amount;
                }else{
                    isValidAmount = false;
                }
            }
        } while (!isValidAmount);

        return null;
    }

    public boolean askForNewTransaction() {
        System.out.println("New transaction? y/n");
        Scanner scanner = new Scanner(System.in);
        if (scanner.next().equalsIgnoreCase("y")) {
            return true;
        }
        return false;
    }
}
