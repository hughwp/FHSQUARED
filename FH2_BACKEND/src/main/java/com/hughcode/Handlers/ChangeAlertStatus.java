package com.hughcode.Handlers;

import com.hughcode.DAO.AlertsDAO;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ChangeAlertStatus implements HttpHandler {

    private final AlertsDAO alertsDAO = new AlertsDAO();

    @Override
    public void handle(HttpExchange exchange) {
        try {
            String[] params = exchange.getRequestURI().getQuery().split("&");

            int alertId = Integer.parseInt(params[0].split("=")[1]);
            String status = params[1].split("=")[1];

            alertsDAO.updateStatus(alertId, status);

            String response = "Alert status updated";
            exchange.sendResponseHeaders(200, response.length());
            exchange.getResponseBody().write(response.getBytes());
            exchange.getResponseBody().close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
