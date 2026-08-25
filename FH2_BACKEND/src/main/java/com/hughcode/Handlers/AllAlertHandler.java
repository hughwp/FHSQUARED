package com.hughcode.Handlers;

import com.google.gson.Gson;
import com.hughcode.Alert;
import com.hughcode.DAO.AlertsDAO;
import com.hughcode.SendResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.util.List;

public class AllAlertHandler implements HttpHandler {

    private final AlertsDAO alertsDAO = new AlertsDAO();
    private final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) {
        try {
            if ("GET".equals(exchange.getRequestMethod())) {
                List<Alert> alerts = alertsDAO.getAllAlerts();
                SendResponse.sendResponse(exchange, 200, gson.toJson(alerts));
            } else {
                SendResponse.sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
            }
        } catch (Exception e) {
            try {
                SendResponse.sendResponse(exchange, 500, "{\"error\":\"Internal server error\"}");
            } catch (Exception ex) {
                System.out.println("Error sending error response: " + ex.toString());
            }
        }
    }
}