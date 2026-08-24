package com.hughcode.rules;
import com.hughcode.Transaction;

public class TransactionAgainstRules {

    public static boolean test(Transaction transaction){

        ThresholdRule thresholdRule = new ThresholdRule();
        NewPayeeRule newPayeeRule  = new NewPayeeRule();


        return thresholdRule.evaluate(transaction) && newPayeeRule.evaluate(transaction);

    }

}
