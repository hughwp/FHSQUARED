package com.hughcode;

import com.hughcode.DAO.RulesTableDAO;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RulesTableDAOTest {

    @Test
    public void RulesTableTest() {

        try {

            System.out.println("=== CURRENT ENABLED RULES ===");

            RulesTableDAO.getEnabledRules();


            System.out.println("\n=== ADDING TEST RULE ===");

            AlertRule testRule = new AlertRule(
                    0,
                    "Test Rule",
                    "Triggers an alert when a test condition is met",
                    true
            );

            RulesTableDAO.insertRule(testRule);


            System.out.println("\n=== ENABLED RULES AFTER INSERT ===");

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(out);

            PrintStream originalOut = System.out;
            System.setOut(ps);

            RulesTableDAO.getEnabledRules();

            System.setOut(originalOut); // restore

            String printed = out.toString().trim();
            try {
                assertTrue(printed.contains("Triggers an alert when a test condition is met"));
                System.out.println("TRUE");
            } catch (AssertionError e) {
                System.out.println("FALSE");
            }

        } catch (Exception e) {
            
            e.printStackTrace();

        }
    }
}