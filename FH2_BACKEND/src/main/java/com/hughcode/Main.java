package com.hughcode;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.*;
import com.hughcode.DAO.TransactionPublisherDAO;
import com.hughcode.Transaction;
import com.hughcode.DatabaseConnection;
import com.hughcode.rules.TransactionAgainstRules;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<Transaction> transactionQueue = new LinkedBlockingQueue<>();
        Thread subscriberThread = new Thread(() -> {
            new TransactionPublisherDAO(transactionQueue).subscribe();
        });

        subscriberThread.start();

        while (true) {
            try {
                Transaction transaction = transactionQueue.take();
                TransactionAgainstRules.evaluate(transaction);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            catch (SQLException e){
                System.out.println(e.toString());
            }
        }
    }
}