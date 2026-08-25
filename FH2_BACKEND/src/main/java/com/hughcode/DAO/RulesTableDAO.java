package com.hughcode.DAO;

import com.hughcode.AlertRule;
import com.hughcode.DatabaseConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RulesTableDAO {

    public static boolean isRuleEnabled(int ruleId) throws SQLException {
        String query = "SELECT enabled FROM alert_rules WHERE rule_id = ?";

        try (var statement = DatabaseConnection.getConnection().prepareStatement(query)) {
            statement.setInt(1, ruleId);

            try (var resultSet = statement.executeQuery()) {
                return resultSet.next() && resultSet.getBoolean("enabled");
            }
        }
    }

    public static void disableRule(int ruleId) throws SQLException {
        setRuleEnabled(ruleId, false);
    }

    public static void enableRule(int ruleId) throws SQLException {
        setRuleEnabled(ruleId, true);
    }

    private static void setRuleEnabled(int ruleId, boolean enabled) throws SQLException {
        String query = "UPDATE alert_rules SET enabled = ? WHERE rule_id = ?";

        try (var statement = DatabaseConnection.getConnection().prepareStatement(query)) {
            statement.setBoolean(1, enabled);
            statement.setInt(2, ruleId);
            statement.executeUpdate();
        }
    }

    public static List<AlertRule> getAllRules() throws SQLException {

        String query = "SELECT * FROM alert_rules";
        List<AlertRule> rules = new ArrayList<>();

        try (var statement = DatabaseConnection.getConnection().prepareStatement(query);
             var resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                rules.add(new AlertRule(
                        resultSet.getInt("rule_id"),
                        resultSet.getString("rule_name"),
                        resultSet.getString("rule_description"),
                        resultSet.getBoolean("enabled")
                ));
            }
        }

        return rules;
    }

    // this just adds a new rule
    public static void insertRule(AlertRule rule) throws SQLException {

        String query = "INSERT INTO alert_rules " +
                "(rule_name, rule_description, enabled) " +
                "VALUES ('" + rule.ruleName + "', '" +
                rule.ruleDescription + "', " +
                rule.enabled + ")";

        DatabaseConnection.getConnection()
                .createStatement()
                .executeUpdate(query);
    }


    // this only gets the rules that are triggered/enabled
    public static void getEnabledRules() throws SQLException {

        String query = "SELECT * FROM alert_rules WHERE enabled = TRUE";

        var resultSet = DatabaseConnection.getConnection()
                .createStatement()
                .executeQuery(query);

        while (resultSet.next()) {

            System.out.println(
                    resultSet.getInt("rule_id") + " | " +
                            resultSet.getString("rule_name") + " | " +
                            resultSet.getString("rule_description") + " | " +
                            resultSet.getBoolean("enabled")
            );
        }
    }


    // this changes existing rules
    public static void updateRule(AlertRule rule) throws SQLException {

        String query = "UPDATE alert_rules SET " +
                "rule_name = '" + rule.ruleName + "', " +
                "rule_description = '" + rule.ruleDescription + "', " +
                "enabled = " + rule.enabled +
                " WHERE rule_id = " + rule.ruleId;

        DatabaseConnection.getConnection()
                .createStatement()
                .executeUpdate(query);
    }
}
