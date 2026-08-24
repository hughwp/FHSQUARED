package com.hughcode.DAO;

import com.hughcode.AlertRule;
import com.hughcode.DatabaseConnection;

import java.sql.SQLException;

public class RulesTableDAO {

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
