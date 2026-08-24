package com.hughcode;
import java.sql.Connection;
import java.util.concurrent.*;
import com.hughcode.DAO.TransactionPublisherDAO;
import com.hughcode.Transaction;
import com.hughcode.DatabaseConnection;

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
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}