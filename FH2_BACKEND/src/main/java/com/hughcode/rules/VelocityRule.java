package com.hughcode.rules;

import com.hughcode.DAO.TransactionTableDAO;
import com.hughcode.DAO.RulesTableDAO;
import com.hughcode.Transaction;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class VelocityRule{

    public List<Transaction> evaluate_special_case(Transaction transaction){

        int timeframe = 10;
        try {
            timeframe = (int) RulesTableDAO.fetchRuleDataAsDouble(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Transaction> violatedTransactions = new ArrayList<>();
        try {
            String[] uniqueAccounts = TransactionTableDAO.getUniqueAccountIDsInTimeFrame(transaction, timeframe);

            for (String accountId : uniqueAccounts) {
                Transaction txn = TransactionTableDAO.getTransactionCountByAccountInTimeFrame(accountId, timeframe);
                if (txn != null) {
                    violatedTransactions.add(txn);
                }
            }
            return violatedTransactions;
        } catch (SQLException e) {
            e.printStackTrace();
            return violatedTransactions;
        }
    }
}