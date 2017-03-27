package com.praveen;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Created by praveekr on 3/17/17.
 */
@SpringBootApplication
@Service
public class LoadUserTransaction {

    String userEmail = "interview@levelmoney.com";
    String password = "password2";
    Long userId = 1110590645L;
    String userAuthToken = "3D867A3CB6ACA1280B63552ADC8071BF";
    String userAPIToken = "AppTokenForInterview";
    private static final Logger log = LoggerFactory.getLogger(LoadUserTransaction.class);
    RestTemplate restTemplate;


    public HashMap<String, Map<String, List<Transaction>>> loadUserTransation(boolean ignoreDonuts){
        TransactionResponse response = fetchTransactions(restTemplate);
        HashMap<String, Map<String, List<Transaction>>> mapOfTransactionsPerMonth = new HashMap<>();
        if(!response.error.equals("no-error")){
            return mapOfTransactionsPerMonth;
        }
        for(int i=0;i<response.transactions.length;i++){
            Transaction transaction = response.transactions[i];
            String transactionMonth = transaction.transactionTime.substring(0,7);
            //log.info("Transaction Amount - "+transaction.amount.intValue());
            //log.info("Transaction Time = "+ transactionMonth);
            //log.info("Check For donuts"+transaction.merchant);
            if(ignoreDonuts && isTransactionForDonuts(transaction)){
                continue;
            }
            //Calendar cal = Calendar.getInstance();
            //cal.setTimeInMillis(transaction.clearDate);
            Map<String, List<Transaction>> mapOfAccounts = mapOfTransactionsPerMonth.get(transactionMonth);
            if(mapOfAccounts == null ) {
                mapOfAccounts = new HashMap();
                mapOfAccounts.put("income",new ArrayList<>());
                mapOfAccounts.put("expense", new ArrayList<>());
                mapOfTransactionsPerMonth.put(transactionMonth,mapOfAccounts);
            }
            //log.info("Transaction clearance time - "+ cal.getTime());

            if(transaction.amount.intValue()>=0){
                mapOfAccounts.get("income").add(transaction);
            }
            if(transaction.amount.intValue()<0){
                mapOfAccounts.get("expense").add(transaction);
            }
        }

        return mapOfTransactionsPerMonth;
    }

    private boolean isTransactionForDonuts(Transaction transaction ){
        return (transaction.merchant.contains("Krispy Kreme Donuts") || transaction.merchant.contains("DUNKIN"));
     }
    public LoadUserTransaction(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate = restTemplateBuilder.build();
    }
    /*
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }


    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        return args -> {
            fetchTransactions(restTemplate);
        };
    }
*/


    public TransactionResponse fetchTransactions(RestTemplate restTemplate) {
        Map<String, Map> arguments = new HashMap<String,Map>();
        Map<String, Object> userAuthParams = new HashMap<String, Object>();
        userAuthParams.put("uid",1110590645);
        userAuthParams.put("token","3D867A3CB6ACA1280B63552ADC8071BF");
        userAuthParams.put("api-token", "AppTokenForInterview");
        userAuthParams.put("json-strict-mode", Boolean.FALSE);
        userAuthParams.put("json-verbose-response", Boolean.FALSE);
        arguments.put("args",userAuthParams);
        log.info("WE ARE GOING TO HIT THE URL NOW>>>>>");
        TransactionResponse response = restTemplate.postForObject(
                "https://2016.api.levelmoney.com/api/v2/core/get-all-transactions",arguments,
                TransactionResponse.class);
        log.info(response.toString());
        return response;
    }

    /*
    public static void main(String[] args){

        SpringApplication.run(LoadUserTransaction.class);

    }
    */
}
