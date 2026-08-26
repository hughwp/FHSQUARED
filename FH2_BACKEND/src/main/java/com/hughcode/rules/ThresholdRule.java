package com.hughcode.rules;
import com.hughcode.Transaction;
import com.hughcode.DAO.RulesTableDAO;

import java.sql.SQLException;

public class ThresholdRule implements Rule{

    public int evaluate(Transaction transaction){
        try {
            double threshold = RulesTableDAO.fetchRuleDataAsDouble(1);
            if (transaction.amount > threshold){
                return (int) transaction.amount;
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return 0;
    }
}
