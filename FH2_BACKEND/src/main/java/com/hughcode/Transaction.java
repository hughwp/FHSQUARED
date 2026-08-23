package com.hughcode;

import java.time.LocalDateTime;

public class Transaction {
    public String transactionId;
    public String accountId;
    public String payerFname;
    public String payerLname;
    public String payeeId;
    public String merchantName;
    public double amount;
    public String transactionType;
    public LocalDateTime timestamp;
    public String status;

    public Transaction(String transactionId, String accountId, String payerFname, String payerLname,
                       String payeeId, String merchantName, double amount, String transactionType,
                       LocalDateTime timestamp, String status) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.payerFname = payerFname;
        this.payerLname = payerLname;
        this.payeeId = payeeId;
        this.merchantName = merchantName;
        this.amount = amount;
        this.transactionType = transactionType;
        this.timestamp = timestamp;
        this.status = status;
    }
}