package com.hughcode;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.concurrent.*;
import com.hughcode.DAO.TransactionPublisherDAO;
import com.hughcode.Handlers.AllAlertHandler;
import com.hughcode.Handlers.ChangeAlertStatus;
import com.hughcode.Handlers.DisableRuleHandler;
import com.hughcode.Handlers.EnableRuleHandler;
import com.hughcode.Handlers.GetRulesHandler;
import com.hughcode.Handlers.GetTxnByIdHandler;
import com.hughcode.Transaction;
import com.hughcode.rules.TransactionAgainstRules;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
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
        server.createContext("/get_all_alerts", withCorsDisabled(new AllAlertHandler()));
        server.createContext("/get_txn_by_id", withCorsDisabled(new GetTxnByIdHandler()));
        server.createContext("/change_status", withCorsDisabled(new ChangeAlertStatus()));
        server.createContext("/get_rules", withCorsDisabled(new GetRulesHandler()));
        server.createContext("/enable_rule", withCorsDisabled(new EnableRuleHandler()));
        server.createContext("/disable_rule", withCorsDisabled(new DisableRuleHandler()));
        server.setExecutor(null);
        server.start();
    }

    private static HttpHandler withCorsDisabled(HttpHandler handler) {
        return exchange -> {
            addPermissiveCorsHeaders(exchange);

            if ("OPTIONS".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(204, -1);
                exchange.close();
                return;
            }

            handler.handle(exchange);
        };
    }

    private static void addPermissiveCorsHeaders(HttpExchange exchange) throws IOException {
        String requestedHeaders = exchange.getRequestHeaders()
                .getFirst("Access-Control-Request-Headers");

        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS");
        exchange.getResponseHeaders().set(
                "Access-Control-Allow-Headers", requestedHeaders == null ? "*" : requestedHeaders);
        exchange.getResponseHeaders().set("Access-Control-Expose-Headers", "*");
        exchange.getResponseHeaders().set("Access-Control-Max-Age", "86400");
    }
}
