package com.hughcode.DAO;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.concurrent.BlockingQueue;

import com.google.gson.*;
import com.hughcode.Transaction;

public class TransactionPublisherDAO {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                            LocalDateTime.parse(json.getAsString()))
            .create();

    private BlockingQueue<Transaction> transactionQueue;

    public TransactionPublisherDAO(BlockingQueue<Transaction> transactionQueue) {
        this.transactionQueue = transactionQueue;
    }

    public void subscribe() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://transaction-publisher-service-883109165242.us-central1.run.app/stream"))
                .GET()
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofLines())
                .thenAccept(response -> {
                    response.body()
                            .filter(line -> line.startsWith("data:"))
                            .map(line -> line.substring(5).trim())
                            .forEach(data -> {
                                try {
                                    Transaction currentTransaction = gson.fromJson(data, Transaction.class);
                                    transactionQueue.put(currentTransaction);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                    // Consider breaking the stream if you want graceful shutdown
                                } catch (JsonSyntaxException e) {
                                    System.err.println("Invalid JSON: " + data + " - " + e.getMessage());
                                }
                            });
                })
                .exceptionally(ex -> {
                    System.err.println("SSE connection failed: " + ex.getMessage());
                    ex.printStackTrace();
                    return null;
                })
                .join();
    }
}