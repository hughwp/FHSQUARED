package com.hughcode.rules;
import com.hughcode.Transaction;

public interface Rule {
    public int evaluate(Transaction transaction);
}
