package com.hughcode.Handlers;

import com.hughcode.DAO.RulesTableDAO;
import com.hughcode.SendResponse;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.sql.SQLException;

public class ChangeRuleDataHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            int ruleId = getIntQueryParam(exchange, "rule_id");
            int ruleData = getIntQueryParam(exchange, "rule_data");

            RulesTableDAO.changeRuleData(ruleId, ruleData);
            SendResponse.sendResponse(exchange, 200, "{\"message\":\"Rule data updated\"}");
        } catch (IllegalArgumentException e) {
            SendResponse.sendResponse(exchange, 400, "{\"error\":\"rule_id and rule_data are required\"}");
        } catch (SQLException e) {
            System.out.println("Error changing rule data: " + e);
            SendResponse.sendResponse(exchange, 500, "{\"error\":\"Internal server error\"}");
        }
    }

    private int getIntQueryParam(HttpExchange exchange, String key) {
        String query = exchange.getRequestURI().getQuery();
        if (query == null) {
            throw new IllegalArgumentException("Missing query");
        }

        for (String parameter : query.split("&")) {
            String[] parts = parameter.split("=", 2);
            if (parts.length == 2 && key.equals(parts[0])) {
                return Integer.parseInt(parts[1]);
            }
        }

        throw new IllegalArgumentException("Missing " + key);
    }
}
