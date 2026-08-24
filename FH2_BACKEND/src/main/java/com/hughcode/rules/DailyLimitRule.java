package com.hughcode.rules;
import com.hughcode.Transaction;
import com.hughcode.DAO.TransactionTableDAO;
import java.sql.SQLException;

public class DailyLimitRule implements Rule{

    private static final double DAILY_LIMIT = 20000.0;

    public boolean evaluate(Transaction transaction){
        try {
            double totalToday = TransactionTableDAO.getTotalTransactionInLastDay(transaction);
            return (totalToday + transaction.amount) <= DAILY_LIMIT;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}