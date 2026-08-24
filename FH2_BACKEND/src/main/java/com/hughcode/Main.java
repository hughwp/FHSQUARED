package com.hughcode;
import java.sql.Connection;
import java.util.concurrent.*;
import com.hughcode.DAO.TransactionPublisherDAO;
import com.hughcode.Transaction;
import com.hughcode.DatabaseConnection;
import com.hughcode.rules.TransactionAgainstRules;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        try{
            Connection connection = DatabaseConnection.getConnection();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }


        BlockingQueue<Transaction> transactionQueue = new LinkedBlockingQueue<>();
        Thread subscriberThread = new Thread(() -> {
            new TransactionPublisherDAO(transactionQueue).subscribe();
        });

        subscriberThread.start();

        while (true) {
            try {
                Transaction transaction = transactionQueue.take();
                System.out.println("Received transaction: " + transaction.toString());
                System.out.println("Processing  of the amount £"+transaction.amount);
                System.out.println(TransactionAgainstRules.test(transaction));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}