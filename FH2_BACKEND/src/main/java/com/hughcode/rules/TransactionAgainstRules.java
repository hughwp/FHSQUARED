package com.hughcode.rules;
import com.hughcode.Alert;
import com.hughcode.Transaction;
import com.hughcode.DAO.AlertsDAO;
import com.hughcode.DAO.RulesTableDAO;
import com.hughcode.DAO.TransactionTableDAO;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class TransactionAgainstRules {

    public static void evaluate(Transaction transaction) throws SQLException {

        TransactionTableDAO.insertTransaction(transaction);

        ThresholdRule thresholdRule = new ThresholdRule();
        NewPayeeRule newPayeeRule = new NewPayeeRule();
        DailyLimitRule dailyLimitRule = new DailyLimitRule();

        AlertsDAO alertsDAO = new AlertsDAO();

        System.out.println("-----------------------------------------------");

        if (RulesTableDAO.isRuleEnabled(1) && !(thresholdRule.evaluate(transaction))){
            Alert alert = new Alert(0, 1, transaction.transactionId, "HIGH", "", "OPEN", LocalDateTime.now(), null);
            alertsDAO.create_Alert(alert);
            System.out.println("ALERT CREATED: Threshold Rule violated for transaction " + transaction.transactionId);
        }

        if (RulesTableDAO.isRuleEnabled(3) && !(newPayeeRule.evaluate(transaction))){
            Alert alert = new Alert(0, 3, transaction.transactionId, "LOW", "", "OPEN", LocalDateTime.now(), null);
            alertsDAO.create_Alert(alert);
            System.out.println("ALERT CREATED: New Payee Rule violated for transaction " + transaction.transactionId);
        }

        if (RulesTableDAO.isRuleEnabled(4) && !(dailyLimitRule.evaluate(transaction))){
            Alert alert = new Alert(0, 4, transaction.transactionId, "HIGH", "", "OPEN", LocalDateTime.now(), null);
            alertsDAO.create_Alert(alert);
            System.out.println("ALERT CREATED: Daily Limit Rule violated for transaction " + transaction.transactionId);
        }

        System.out.println("-----------------------------------------------");
    }

}
