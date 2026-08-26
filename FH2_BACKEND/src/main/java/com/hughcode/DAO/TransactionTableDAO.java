package com.hughcode.DAO;

import com.hughcode.DatabaseConnection;
import com.hughcode.Transaction;
import com.google.gson.*;

import java.time.format.DateTimeFormatter;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class TransactionTableDAO {

    private Connection conn;

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) -> {
                        String timestampString = json.getAsString().replace(" ", "T");
                        return LocalDateTime.parse(timestampString);
                    })
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                            new JsonPrimitive(src.toString()))
            .create();

    public TransactionTableDAO() {
        conn = DatabaseConnection.getConnection();
    }

    public static Gson getGson() {
        return gson;
    }

    public static int checkIfPayeeExistsAlready(Transaction transaction) throws SQLException {
        String query = "SELECT * FROM transactions WHERE payee_id = '" + transaction.payeeId +
                "' AND account_id = '" + transaction.accountId + "'";
        var resultSet = DatabaseConnection.getConnection().createStatement().executeQuery(query);
        int count = 0;
        while (resultSet.next()) {
            count++;
        }
        return count;
    }

    public static Transaction getTransaction(String transactionId) throws SQLException {
        String query = "SELECT * FROM transactions WHERE transaction_id = '" + transactionId + "'";
        var resultSet = DatabaseConnection.getConnection().createStatement().executeQuery(query);

        if (resultSet.next()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

            Transaction transaction = new Transaction(
                    resultSet.getString("transaction_id"),
                    resultSet.getString("account_id"),
                    resultSet.getString("payer_fname"),
                    resultSet.getString("payer_lname"),
                    resultSet.getString("payee_id"),
                    resultSet.getString("merchantName"),
                    resultSet.getDouble("amount"),
                    resultSet.getString("transaction_type"),
                    LocalDateTime.parse(resultSet.getString("timestamp"), formatter),
                    resultSet.getString("status")
            );
            return transaction;
        }
        return null;
    }

    public static void insertTransaction(Transaction transaction) throws SQLException {
        String query = "INSERT INTO transactions (transaction_id, account_id, payer_fname, payer_lname, payee_id, merchantName, amount, transaction_type, timestamp, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (var statement = DatabaseConnection.getConnection().prepareStatement(query)) {
            statement.setString(1, transaction.transactionId);
            statement.setString(2, transaction.accountId);
            statement.setString(3, transaction.payerFname);
            statement.setString(4, transaction.payerLname);
            statement.setString(5, transaction.payeeId);
            statement.setString(6, transaction.merchantName);
            statement.setDouble(7, transaction.amount);
            statement.setString(8, transaction.transactionType);
            statement.setObject(9, transaction.timestamp);
            statement.setString(10, transaction.status);
            statement.executeUpdate();
        }
    }

    public static double getTotalTransactionInLastDay(Transaction transaction) throws SQLException{
        String query = "SELECT SUM(amount) as total FROM transactions WHERE account_id = '" + transaction.accountId + "' AND timestamp >= NOW() - INTERVAL '1 day'";
        var resultSet = DatabaseConnection.getConnection().createStatement().executeQuery(query);

        if (resultSet.next()) {
            return resultSet.getDouble("total");
        }
        return 0.0;
    }

    public static String[] getUniqueAccountIDsInTimeFrame(Transaction transaction, int timeFrame) throws SQLException{
        String query = "SELECT DISTINCT account_id FROM transactions WHERE timestamp >= NOW() - INTERVAL '" + timeFrame + " minutes'";
        var resultSet = DatabaseConnection.getConnection().createStatement().executeQuery(query);

        java.util.List<String> accountIds = new java.util.ArrayList<>();
        while (resultSet.next()) {
            accountIds.add(resultSet.getString("account_id"));
        }
        return accountIds.toArray(new String[0]);
    }

    public static Transaction getTransactionCountByAccountInTimeFrame(String accountId, int timeFrame) throws SQLException{

        String query = "SELECT * FROM transactions WHERE account_id = '" + accountId + "' AND timestamp >= NOW() - INTERVAL '" + timeFrame + " minutes' ORDER BY timestamp DESC LIMIT 1";
        var resultSet = DatabaseConnection.getConnection().createStatement().executeQuery(query);

        if (resultSet.next()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

            Transaction transaction = new Transaction(
                    resultSet.getString("transaction_id"),
                    resultSet.getString("account_id"),
                    resultSet.getString("payer_fname"),
                    resultSet.getString("payer_lname"),
                    resultSet.getString("payee_id"),
                    resultSet.getString("merchantName"),
                    resultSet.getDouble("amount"),
                    resultSet.getString("transaction_type"),
                    LocalDateTime.parse(resultSet.getString("timestamp"), formatter),
                    resultSet.getString("status")
            );
            return transaction;
        }
        return null;
    }
}
