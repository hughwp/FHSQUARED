package com.hughcode.Handlers;

import com.google.gson.Gson;
import com.hughcode.AlertRule;
import com.hughcode.DAO.RulesTableDAO;
import com.hughcode.SendResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class GetRulesHandler implements HttpHandler {

    private static final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            SendResponse.sendResponse(exchange, 405, "{\"error\":\"Method not allowed\"}");
            return;
        }

        try {
            List<AlertRule> rules = RulesTableDAO.getAllRules();
            SendResponse.sendResponse(exchange, 200, gson.toJson(rules));
        } catch (SQLException e) {
            System.out.println("Error retrieving rules: " + e);
            SendResponse.sendResponse(exchange, 500, "{\"error\":\"Internal server error\"}");
        }
    }
}
