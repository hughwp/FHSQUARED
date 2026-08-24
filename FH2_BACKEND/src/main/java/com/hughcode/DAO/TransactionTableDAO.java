package com.hughcode.DAO;

import com.hughcode.DatabaseConnection;
import com.hughcode.Transaction;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionTableDAO {

    private Connection conn;

    public TransactionTableDAO() {
        conn = DatabaseConnection.getConnection();
    }

    public static int checkIfPayeeExistsAlready(Transaction transaction) throws SQLException {
        String query = "SELECT * FROM transactions WHERE payee_id = '" + transaction.payeeId + "'";
        var resultSet = DatabaseConnection.getConnection().createStatement().executeQuery(query);
        int count = 0;
        while (resultSet.next()) {
            count++;
        }
        return count;
    }

    public static void insertTransaction(Transaction transaction) throws SQLException {
        String query = "INSERT INTO transactions (transaction_id, account_id, payer_fname, payer_lname, payee_id, merchantName, amount, transaction_type, timestamp, status) " +
                "VALUES ('" + transaction.transactionId + "', '" + transaction.accountId + "', '" + transaction.payerFname + "', '" + transaction.payerLname + "', '" + transaction.payeeId + "', '" + transaction.merchantName + "', " + transaction.amount + ", '" + transaction.transactionType + "', '" + transaction.timestamp + "', '" + transaction.status + "')";
        DatabaseConnection.getConnection().createStatement().executeUpdate(query);
    }


    public static double getTotalTransactionInLastDay(Transaction transaction) throws SQLException{
        String query = "SELECT SUM(amount) as total FROM transactions WHERE account_id = '" + transaction.accountId + "' AND timestamp >= NOW() - INTERVAL '1 day'";
        var resultSet = DatabaseConnection.getConnection().createStatement().executeQuery(query);

        if (resultSet.next()) {
            return resultSet.getDouble("total");
        }
        return 0.0;
    }

}
