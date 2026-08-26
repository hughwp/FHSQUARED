package com.hughcode.Handlers;

import com.hughcode.DAO.RulesTableDAO;
import com.hughcode.SendResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;

public class FetchCurrentValueForRuleHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            int ruleId = getRuleId(exchange);
            String ruleData = RulesTableDAO.fetchCurrentValueForRule(ruleId);
            SendResponse.sendResponse(exchange, 200, "{\"rule_data\":\"" + ruleData.replace("\"", "\\\"") + "\"}");
        } catch (IllegalArgumentException e) {
            SendResponse.sendResponse(exchange, 400, "{\"error\":\"A valid rule_id is required\"}");
        } catch (SQLException e) {
            System.out.println("Error fetching current value for rule: " + e);
            SendResponse.sendResponse(exchange, 500, "{\"error\":\"Internal server error\"}");
        }
    }

    private int getRuleId(HttpExchange exchange) {
        String query = exchange.getRequestURI().getQuery();
        if (query == null) {
            throw new IllegalArgumentException("Missing rule_id");
        }

        for (String parameter : query.split("&")) {
            String[] parts = parameter.split("=", 2);
            if (parts.length == 2 && "rule_id".equals(parts[0])) {
                return Integer.parseInt(parts[1]);
            }
        }

        throw new IllegalArgumentException("Missing rule_id");
    }
}
