package com.hughcode.Handlers;

import com.hughcode.SendResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class AllAlertHandler implements HttpHandler {

    public AllAlertHandler() {}

    @Override
    public void handle(HttpExchange exchange) {
        try {
            if ("GET".equals(exchange.getRequestMethod())) {
                SendResponse.sendResponse(exchange, 200, "AllAlertHandler is up and running");
            } else {
                SendResponse.sendResponse(exchange, 405, "Method not allowed");
            }
        } catch (Exception e) {
            try {
                SendResponse.sendResponse(exchange, 500, "Internal server error");
            } catch (Exception ex) {
                System.out.println("Error sending error response: " + ex.toString());
            }
        }
    }
}