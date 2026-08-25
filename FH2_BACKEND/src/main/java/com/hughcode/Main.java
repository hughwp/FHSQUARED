package com.hughcode;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.*;
import com.hughcode.DAO.TransactionPublisherDAO;
import com.hughcode.Handlers.AllAlertHandler;
import com.hughcode.Handlers.ChangeAlertStatus;
import com.hughcode.Handlers.GetTxnByIdHandler;
import com.hughcode.Transaction;
import com.hughcode.DatabaseConnection;
import com.hughcode.rules.TransactionAgainstRules;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.hughcode.SendResponse;
import com.sun.net.httpserver.HttpServer;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        BlockingQueue<Transaction> transactionQueue = new LinkedBlockingQueue<>();
        Thread subscriberThread = new Thread(() -> {
            new TransactionPublisherDAO(transactionQueue).subscribe();
        });

        Thread httpThread = new Thread(() -> {
            try {
                startHttpServer();
            } catch (Exception e) {
                System.out.println("HTTP Server error: " + e.toString());
            }
        });


        subscriberThread.start();
        httpThread.start();

        while (true) {
            try {
                Transaction transaction = transactionQueue.take();
                TransactionAgainstRules.evaluate(transaction);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }

    private static void startHttpServer() throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/get_all_alerts", new AllAlertHandler());
        server.createContext("/get_txn_by_id", new GetTxnByIdHandler());
        server.createContext("/change_status", new ChangeAlertStatus());
        server.setExecutor(null);
        server.start();
    }
}