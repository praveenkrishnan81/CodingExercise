package com.praveen;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by praveekr on 3/17/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionResponse {

    String error;
    Transaction[] transactions;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Transaction[] getTransactions() {
        return transactions;
    }

    public void setTransactions(Transaction[] transactions) {
        this.transactions = transactions;
    }

    public String toString(){
        return "TransactionResponse{" +
                "error='" + error + '\'' +
                ", Number Of Transactions =" + transactions.length +
                '}';
    }
}
