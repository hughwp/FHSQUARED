package com.hughcode;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            String dbHost = "167.99.0.151";
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://" + dbHost + ":5433/postgres",
                    "postgres", "FH2"
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize database connection", e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}