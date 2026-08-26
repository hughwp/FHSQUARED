package com.hughcode.rules;
import com.hughcode.Transaction;
import com.hughcode.DAO.TransactionTableDAO;
import java.sql.SQLException;

public class DailyLimitRule implements Rule{

    private static final double DAILY_LIMIT = 200000.0;

    public int evaluate(Transaction transaction){
        try {
            double totalToday = TransactionTableDAO.getTotalTransactionInLastDay(transaction);
            return (int) totalToday;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}