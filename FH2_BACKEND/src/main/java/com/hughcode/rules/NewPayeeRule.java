package com.hughcode.rules;

public class NewPayeeRule implements Rule{
    public boolean evaluate(Transaction transaction){
        return true;
    }
}
