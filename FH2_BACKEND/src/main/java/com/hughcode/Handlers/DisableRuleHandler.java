package com.hughcode.Handlers;

import com.hughcode.DAO.RulesTableDAO;
import com.hughcode.SendResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class DisableRuleHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            int ruleId = getRuleId(exchange);
            RulesTableDAO.disableRule(ruleId);
            SendResponse.sendResponse(exchange, 200, "Rule disabled");
        } catch (IllegalArgumentException e) {
            SendResponse.sendResponse(exchange, 400, "{\"error\":\"A valid rule_id is required\"}");
        } catch (Exception e) {
            System.out.println("Error disabling rule: " + e);
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
