package com.hughcode.rules;
import com.hughcode.Transaction;

public interface Rule {
    public boolean evaluate(Transaction transaction);
}
