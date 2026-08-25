package com.hughcode.Handlers;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.hughcode.SendResponse;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.*;
import com.hughcode.DAO.TransactionTableDAO;

public class GetTxnByIdHandler implements HttpHandler {

    public GetTxnByIdHandler() {}

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
                            LocalDateTime.parse(json.getAsString(), formatter))
            .create();

    @Override
    public void handle(HttpExchange exchange) {
        try {
            System.out.println("GetTxnByIdHandler endpoint called");

            if ("GET".equals(exchange.getRequestMethod())) {

                String query = exchange.getRequestURI().getQuery();
                String id = null;

                if (query != null && query.contains("id=")) {
                    id = URLDecoder.decode(query.split("id=")[1].split("&")[0], "UTF-8");
                    System.out.println("Transaction ID extracted: " + id);
                    SendResponse.sendResponse(exchange, 200, TransactionTableDAO.getGson().toJson(TransactionTableDAO.getTransaction(id)));
                } else {
                    System.out.println("Missing id parameter in query");
                    SendResponse.sendResponse(exchange, 400, "Missing id parameter");
                }
            } else {
                SendResponse.sendResponse(exchange, 405, "Method not allowed");
            }
        } catch (Exception e) {
            System.out.println("Error in GetTxnByIdHandler: " + e.toString());
            e.printStackTrace();
            try {
                SendResponse.sendResponse(exchange, 500, "Internal server error");
            } catch (Exception ex) {
                System.out.println("Error sending error response: " + ex.toString());
            }
        }
    }
}