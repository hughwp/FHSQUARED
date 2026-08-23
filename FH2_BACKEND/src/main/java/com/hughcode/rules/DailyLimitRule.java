package com.hughcode.rules;
import com.hughcode.Transaction;

public class DailyLimitRule implements Rule{
    public boolean evaluate(Transaction transaction){
        return true;
    }
}
