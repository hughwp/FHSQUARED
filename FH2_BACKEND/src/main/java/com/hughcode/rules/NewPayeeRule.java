package com.hughcode.rules;
import com.hughcode.Transaction;
import com.hughcode.DAO.TransactionTableDAO;

import java.sql.SQLException;

public class NewPayeeRule implements Rule {

    public boolean evaluate(Transaction transaction) {
        int result = 0;
        try {
             result = TransactionTableDAO.checkIfPayeeExistsAlready(transaction);

        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return result <= 0;

    }
}
