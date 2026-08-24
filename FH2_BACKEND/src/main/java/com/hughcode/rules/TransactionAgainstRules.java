package com.hughcode.rules;
import com.hughcode.Transaction;

public class TransactionAgainstRules {

    public static void evaluate(Transaction transaction) {

        ThresholdRule thresholdRule = new ThresholdRule();
        NewPayeeRule newPayeeRule = new NewPayeeRule();
        DailyLimitRule dailyLimitRule = new DailyLimitRule();

        System.out.println("-----------------------------------------------");

        System.out.println("PROCESSING TRANSACTION: " + transaction.transactionId);
        System.out.println("OF AMOUNT: " + transaction.amount);

        System.out.println("EVALUATING THRESHOLD RULE");
        System.out.println(thresholdRule.evaluate(transaction));

        System.out.println("EVALUATING NEW PAYEE RULE");
        System.out.println(newPayeeRule.evaluate(transaction));

        System.out.println("EVALUATING DAILY LIMIT RULE");
        System.out.println(dailyLimitRule.evaluate(transaction));

    }

}