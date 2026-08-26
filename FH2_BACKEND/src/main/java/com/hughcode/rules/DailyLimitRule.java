package com.hughcode.rules;
import com.hughcode.Transaction;
import com.hughcode.DAO.TransactionTableDAO;
import com.hughcode.DAO.RulesTableDAO;
import java.sql.SQLException;

public class DailyLimitRule implements Rule{

    public int evaluate(Transaction transaction){
        try {
            double dailyLimit = RulesTableDAO.fetchRuleDataAsDouble(4);
            double totalToday = TransactionTableDAO.getTotalTransactionInLastDay(transaction);
            return totalToday > dailyLimit ? (int) totalToday : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}