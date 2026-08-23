package com.hughcode.rules;
import com.hughcode.Transaction;

public class NewPayeeRule implements Rule{
    public boolean evaluate(Transaction transaction){
        return true;
    }
}
