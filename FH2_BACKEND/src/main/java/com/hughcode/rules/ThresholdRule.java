package com.hughcode.rules;
import com.hughcode.Transaction;

public class ThresholdRule implements Rule{
    public boolean evaluate(Transaction transaction){
        return transaction.amount < 200;
    }
}
