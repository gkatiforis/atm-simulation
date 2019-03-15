package com.katiforis.assignment.atm;

import com.katiforis.assignment.atm.components.Console;
import com.katiforis.assignment.atm.components.MoneyDispenser;
import com.katiforis.assignment.exception.TransactionException;
import com.katiforis.assignment.integration.BankService;
import com.katiforis.assignment.integration.Status;
import mockit.*;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.junit.Assert.assertNotNull;

@RunWith(JMockit.class)
public class ATMTest {

    @Tested
    ATM atm;

    @Injectable
    @Mocked
    Console console;

    @Injectable
    @Mocked
    MoneyDispenser moneyDispenser;

    @Injectable
    @Mocked
    BankService bankService;


    @Test
    public void ATMTest(){

        atm =  new ATM(new Console(), new MoneyDispenser(new HashMap<>()), new BankService());
        assertNotNull(atm.getConsole());
        assertNotNull(atm.getMoneyDispenser());
        assertNotNull(atm.getBankService());
    }

    @Test
    public void startNewSessionSuccessfulWithdrawTest() throws TransactionException {
        Status status = new Status(Status.SUCCESS, "test");
        new Expectations() {{
            console.askForAmountToWithdraw();result = 60;
            moneyDispenser.sufficientMoneyAvailable(anyInt); result = true;
            bankService.sendRequest(); result = status;
            console.askForNewTransaction(); result = false;
        }};

        atm.startNewSession();
    }

    @Test
    public void startNewSessionInsufficientMoneyAvailableTest() {
        new Expectations() {{
            console.askForAmountToWithdraw();
            result = 100;
            moneyDispenser.sufficientMoneyAvailable(anyInt);
            result = false;
            console.askForNewTransaction();
            result = false;
        }};

        atm.startNewSession();
    }


    @Test
    public void startNewSessionInsufficientBalanceFromBankTest() throws TransactionException {
        Status status = new Status(Status.INSUFFICIENT_BALANCE, "test");
        new Expectations() {{
            console.askForAmountToWithdraw();result = 10;
            moneyDispenser.sufficientMoneyAvailable(anyInt); result = true;
            bankService.sendRequest(); result = status;
            console.printErrorMessage(anyString);
        }};

        atm.startNewSession();
    }

    @Test
    public void startNewSessionUnknownErrorFromBankTest() throws TransactionException {
        Status status = new Status(Status.OTHER_FAILURE, "test");
        new Expectations() {{
            console.askForAmountToWithdraw();result = 100;
            moneyDispenser.sufficientMoneyAvailable(anyInt); result = true;
            bankService.sendRequest(); result = status;
            console.printErrorMessage(anyString);
        }};

        atm.startNewSession();
    }

    @Test
    public void startNewSessionInsufficientCashInMachineTest() throws TransactionException {
        Status status = new Status(Status.SUCCESS, "test");
        new Expectations() {{
            console.askForAmountToWithdraw();result = 10;
            moneyDispenser.sufficientMoneyAvailable(anyInt); result = true;
            bankService.sendRequest(); result = status;
            moneyDispenser.dispenseMoney(anyInt);result = new IllegalStateException("test");
            console.printErrorMessage(anyString);
        }};

        atm.startNewSession();
    }

    @Test
    public void startNewSessionNetworkErrorTest() throws TransactionException {
        new Expectations() {{
            console.askForAmountToWithdraw();result = 60;
            moneyDispenser.sufficientMoneyAvailable(anyInt); result = true;
            bankService.sendRequest(); result = new TransactionException("test");
            console.printErrorMessage(anyString);
        }};
        atm.startNewSession();
    }
}
