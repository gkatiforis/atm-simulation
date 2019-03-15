package com.katiforis.assignment.atm.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents money dispenser of the ATM.
 * Responsible to handle the cash in the machine.
 */

public class MoneyDispenser {
    public static final Integer NOTE_50 = 50;
    public static final Integer NOTE_20 = 20;
    public static final Integer SUPPORTED_NOTES[] = {NOTE_50, NOTE_20};
    private final Map<Integer, Integer> notes;

    /**
     * Initialize notes in the machine
     * @param notes
     */
    public MoneyDispenser(Map<Integer, Integer> notes){
        this.notes = notes;
    }

    /**
     * @return the available notes in the machine
     */
    public Map<Integer, Integer> getAvailableNotes() {
        return notes;
    }

    /**
     * Dispense money
     * @param amount to dispense
     * @return Map of notes
     * @throws IllegalStateException in case of no enough cash
     */
    public Map<Integer, Integer> dispenseMoney(Integer amount) throws IllegalStateException{
        Optional<Map<Integer, Integer>> money = fetchMoney(amount, 0);
        money.orElseThrow( () -> new IllegalStateException("Error upon fetching money"));
        money.ifPresent(this::removeMoney);
        return money.get();
    }

    /**
     * Check if sufficient money is available
     * @param moneyToWithdraw amount of money
     * @return true if it is sufficient else false
     */
    public Boolean sufficientMoneyAvailable(Integer moneyToWithdraw) {
         return  fetchMoney(moneyToWithdraw, 0)
                 .isPresent();
    }

    /**
     * Remove money from machine
     * @param moneyToRemove money to remove
     */
    private void removeMoney(Map<Integer, Integer> moneyToRemove){
        moneyToRemove.entrySet()
                .stream()
                .forEach(note -> notes.put(note.getKey(), notes.get(note.getKey()) - note.getValue()));
    }

    /**
     * Fetch money from machine if available
     * @param amount need to be fetched
     * @param fromIndex what position of SUPPORTED_NOTES array to start
     * @return Map of fetched notes else an empty optional
     */
    private Optional<Map<Integer, Integer>> fetchMoney(Integer amount, int fromIndex){
        Map money = new HashMap();
        Integer currentAmount = amount;
        for(int i = fromIndex; i < SUPPORTED_NOTES.length; i++){
            Integer notesCount = currentAmount/SUPPORTED_NOTES[i];
            if(notes.get(SUPPORTED_NOTES[i]) < notesCount){
                continue;
            }
            if(notesCount < 1 && i == SUPPORTED_NOTES.length - 1){
              return   fetchMoney(amount, fromIndex + 1);
            }
            currentAmount %= SUPPORTED_NOTES[i];
            money.put(SUPPORTED_NOTES[i], notesCount);
            if(currentAmount == 0){
                return Optional.of(money);
            }
        }
        return Optional.empty();
    }
}
