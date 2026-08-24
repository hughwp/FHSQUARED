package com.hughcode;

import com.hughcode.DAO.AlertDAO;
import com.hughcode.DAO.AlertsDAO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.PrintStream;

public class AlertsDAOTest {


    @Test
    public void AlertTest() {

        try {

            System.out.println("=== CREATING TEST ALERT ===");

            Alerts testAlert = new Alerts(
                    0,
                    "TXN001",
                    1,
                    "HIGH",
                    "Velocity threshold exceeded",
                    "OPEN",
                    null,
                    null
            );

            int alertId = AlertsDAO.createAlerts(testAlert);

            System.out.println("Created alert ID: " + alertId);

            System.out.println("\n=== GETTING ALERT ===");

            Alerts retrievedAlert = AlertDAO.getAlertById(alertId);

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
