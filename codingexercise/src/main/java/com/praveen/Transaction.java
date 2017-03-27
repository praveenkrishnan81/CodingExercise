package com.praveen;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.deser.std.NumberDeserializers;

import java.math.BigInteger;

/**
 * Created by praveekr on 3/17/17.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {

    @JsonProperty("transaction-id")
    String transactionId;

    @JsonProperty("account-id")
    String accountId;

    @JsonProperty("raw-merchant")
    String rawMerchant;

    @JsonProperty("merchant")
    String merchant;

    @JsonProperty("is-pending")
    boolean isPending;

    @JsonProperty("transaction-time")
    String transactionTime;

    @JsonProperty("amount")
    BigInteger amount;

    @JsonProperty("previous-transaction-id")
    String previousTransactionId;

    @JsonProperty("categorization")
    String categorization;

    @JsonProperty("clear-date")
    long clearDate;

    public String toString(){
        return "Transaction{" +
                " transactionId - " + transactionId +
                " accountId - " + accountId +
                " rawMerchant - " + rawMerchant +
                " transactionTime - "+ transactionTime +
                " amount - " + amount;
    }

}
