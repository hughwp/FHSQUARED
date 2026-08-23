package com.hughcode.rules;

public class DailyLimitRule implements Rule{
    public boolean evaluate(Transaction transaction){
        return true;
    }
}
