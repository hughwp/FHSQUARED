package com.hughcode.rules;
import com.hughcode.Transaction;

public class ThresholdRule implements Rule{
    public int evaluate(Transaction transaction){
        if (transaction.amount > 25000){
            return (int) transaction.amount;
        }
        return 0;
    }
}
