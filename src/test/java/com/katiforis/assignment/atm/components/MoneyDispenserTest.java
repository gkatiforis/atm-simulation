package com.katiforis.assignment.atm.components;

import mockit.Injectable;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(JMockit.class)
public class MoneyDispenserTest {
    @Tested
    MoneyDispenser moneyDispenser;

    @Injectable
    Map<Integer, Integer> notes = new HashMap<>();

    @Before
    public void init(){
        notes.put(MoneyDispenser.NOTE_50, 5);
        notes.put(MoneyDispenser.NOTE_20, 3);
        moneyDispenser = new MoneyDispenser(notes);
    }


    @Test
    public void getAvailableNotesTest() {
        assertNotNull(moneyDispenser.getAvailableNotes());
    }

    @Test
    public void dispenseMoneyTest(){
        Map<Integer, Integer> cash =  moneyDispenser.dispenseMoney(60);
        assertEquals(cash.get(MoneyDispenser.NOTE_20), new Integer(3));
        assertNull(cash.get(MoneyDispenser.NOTE_50));
        assertEquals(notes.get(MoneyDispenser.NOTE_20), new Integer(0));
        assertEquals(notes.get(MoneyDispenser.NOTE_50), new Integer(5));
    }

    @Test(expected = IllegalStateException.class)
    public void dispenseMoneyInsufficientMoneyAvailableTest() throws IllegalStateException{
          moneyDispenser.dispenseMoney(1000);
    }

    @Test(expected = IllegalStateException.class)
    public void dispenseMoneyUnavailableNotesTest() throws IllegalStateException{
        moneyDispenser.dispenseMoney(110);
    }


    @Test
    public void sufficientMoneyAvailableTest() {
      assertTrue(moneyDispenser.sufficientMoneyAvailable(60));
    }

    @Test
    public void sufficientMoneyAvailableInsufficientMoneyAvailableTest() {
        assertFalse(moneyDispenser.sufficientMoneyAvailable(1000));
    }

    @Test
    public void sufficientMoneyAvailableUnavailableNotesTest() {
        assertFalse(moneyDispenser.sufficientMoneyAvailable(110));
    }
}
