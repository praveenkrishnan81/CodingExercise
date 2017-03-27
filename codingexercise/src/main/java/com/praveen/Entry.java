package com.praveen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.*;

/**
 * Created by praveekr on 3/23/17.
 */
@RestController
@EnableAutoConfiguration
@ComponentScan
public class Entry {

    @Autowired LoadUserTransaction loadUserTransaction;
    private static final Logger log = LoggerFactory.getLogger(Entry.class);

    @RequestMapping("/")
    String home() {
        log.info("INSIDE ENTRY POINT DID WE INITIALIZE LoadUserTransation - "+loadUserTransaction);
        return "Hello World!";
    }

    @RequestMapping("/loadTransactions")
    public String getAllTransactions(){
        HashMap<String, Map<String, List<Transaction>>> mapOfTransactions = loadUserTransaction.loadUserTransation(false);
        Iterator<String> iterateMonthlyTransaction = mapOfTransactions.keySet().iterator();
        StringBuilder builder = new StringBuilder();
        while (iterateMonthlyTransaction.hasNext()){
            String month = iterateMonthlyTransaction.next();

            builder.append("{ \"").append(month).append("\" :");
            log.info("MONTH - "+month);
            Map<String,List<Transaction>> incomeExpenseMap = mapOfTransactions.get(month);
            List<Transaction> income = incomeExpenseMap.get("income");
            List<Transaction> expense = incomeExpenseMap.get("expense");
            if(expense.size()>0){
                BigInteger totalExpense = expense.get(0).amount;
                for(int i=1;i<expense.size();i++){
                    totalExpense.add(expense.get(i).amount);
                }
                log.info("Total Expense - "+totalExpense.abs().longValue() + " & Total entries - "+expense.size());
                double averageExpense = totalExpense.abs().longValue()/expense.size()/1000;
                log.info("Avg Expense - "+averageExpense);
                builder.append(" {\"spent\": \"$ ").append(averageExpense).append("\" , \"income\": \"$");
            }else{
                builder.append(" {\"spent\": \"$ ").append(0).append("\" , \"income\": \"$");
            }
            if(income.size() >0 ) {
                BigInteger totalIncome = income.get(0).amount;
                for (int i = 1; i < income.size(); i++) {
                    totalIncome.add(income.get(i).amount);
                }
                log.info("Total Income  - "+totalIncome.abs().longValue()+" & total entries - "+income.size());
                double averageIncome = totalIncome.abs().longValue()/1000;
                log.info("Avg Income - "+averageIncome);
                builder.append(averageIncome).append("\"},");
            }else{
                builder.append(0).append("\"},");
            }
            builder.append("\n");
       }
        return builder.toString();

    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Entry.class, args);
    }

}
