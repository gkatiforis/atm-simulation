package com.katiforis.assignment.integration;

import com.katiforis.assignment.exception.TransactionException;

/**
 * Simulates the integration between ATM and bank services
 */
public class BankService {

    /**
     * Simulates the request to the bank service
     * @return if the transaction was successful true else false
     * @throws Exception in case of any failure (network issue)
     */
    public Status sendRequest() throws TransactionException {
              return new Status(0, "Successful transaction");
    }
}
