package com.hughcode.Handlers;

import com.google.gson.*;
import com.hughcode.Alert;
import com.hughcode.AlertForClient;
import com.hughcode.DAO.AlertsDAO;
import com.hughcode.SendResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AllAlertHandler implements HttpHandler {

    private final AlertsDAO alertsDAO = new AlertsDAO();

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context)
                    -> LocalDateTime.parse(json.getAsString(), formatter)) .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context)
                    -> new JsonPrimitive(src.toString())) .create();;

    @Override
    public void handle(HttpExchange exchange) {
        try {
            if ("GET".equals(exchange.getRequestMethod())) {
                List<AlertForClient> alerts = alertsDAO.getAllAlerts();
                SendResponse.sendResponse(exchange, 200, gson.toJson(alerts));
            } else {
                SendResponse.sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
            }
        } catch (Exception e) {
            try {
                System.out.println("Error handling request: " + e.toString());
                SendResponse.sendResponse(exchange, 500, "{\"error\":\"Internal server error\"}");
            } catch (Exception ex) {
                System.out.println("Error sending error response: " + ex.toString());
            }
        }
    }
}