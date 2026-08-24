package com.hughcode;

import com.hughcode.DAO.AlertsDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlertsDAOTest {
    @Test
    public void AlertTest() {

        try {

            // USE ALERTSDAO class
            AlertsDAO alertsDAO = new AlertsDAO();

            System.out.println("=== CREATING TEST ALERT ===");

            Alert testAlert = new Alert(
                    0,
                    1,
                    "TXN001",
                    "HIGH",
                    "Velocity threshold exceeded",
                    "OPEN",
                    null,
                    null
            );

            int alertId = alertsDAO.create_Alert(testAlert);

            System.out.println("Created alert ID: " + alertId);

            System.out.println("\n=== GETTING ALERT ===");

            Alert retrievedAlert = alertsDAO.getAlertById(alertId);

            assertNotNull(retrievedAlert);

            assertEquals("TXN001", retrievedAlert.getTransactionId());
            assertEquals(1, retrievedAlert.getRuleId());
            assertEquals("HIGH", retrievedAlert.getSeverity());
            assertEquals("Velocity threshold exceeded", retrievedAlert.getReason());

            System.out.println("TRUE");

        } catch (Exception e) {
            e.printStackTrace();
            fail("Alert DAO test failed");
        }
    }
}
